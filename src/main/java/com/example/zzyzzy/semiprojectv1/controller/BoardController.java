package com.example.zzyzzy.semiprojectv1.controller;

import com.example.zzyzzy.semiprojectv1.domain.NewBoardDTO;
import com.example.zzyzzy.semiprojectv1.domain.NewReplyDTO;
import com.example.zzyzzy.semiprojectv1.service.BoardService;
import com.example.zzyzzy.semiprojectv1.service.GoogleRecaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final GoogleRecaptchaService googleRecaptchaService;

    @GetMapping("/list")
    public String list(Model m, @RequestParam(defaultValue = "1") int cpg,
                       HttpServletResponse response) {
        // 클라이언트 캐시 제어
        response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", 0);

        // RequestParam에 defaultValue 를 이용하면 cpg 매개변수가 전달되지 않을 경우 기본 값을 1 전달
        log.info("board/list 호출");
        m.addAttribute("bds", boardService.readBoard(cpg));
        m.addAttribute("cpg", cpg);
        m.addAttribute("stblk", ((cpg -1) /10)*10 +1);
        m.addAttribute("cntpg", boardService.countBoard());
        return "views/board/list";
    }

    @GetMapping("/find")
    public String find(Model m,String findtype, String findkey,
                       @RequestParam(defaultValue = "1") int cpg) {

        m.addAttribute("bds", boardService.findBoard(cpg,findtype,findkey));
        m.addAttribute("cpg", cpg);
        m.addAttribute("stblk", ((cpg -1) /10)*10 +1);
        m.addAttribute("cntpg", boardService.countFindBoard(findtype,findkey));

        return "views/board/list";
    }

    @GetMapping("/view")
    public String view(Model m,int bno) {

        boardService.readOneView(bno);
        m.addAttribute("bd", boardService.readOneBoard(bno));
        m.addAttribute("rps", boardService.readReply(bno));
        return "views/board/view";
    }

    @GetMapping("/write")
    public String write(Model m) {
        // 시스템 환경 변수에 저장된 사이트키 불러옴
        m.addAttribute("sitekey",System.getenv("recaptcha.sitekey"));

        return "views/board/write";
    }

    @PostMapping("/write")
    public ResponseEntity<?> writeok(NewBoardDTO newBoardDTO, @RequestParam("g-recaptcha-response") String gRecaptchaResponse) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        log.info("submit 된 게시글 정보 : {}",newBoardDTO);
        log.info("submit 된 recaptcha 응답 코드 : {}",gRecaptchaResponse);

        try {
            if(!googleRecaptchaService.verifyRecaptcha(gRecaptchaResponse)){
                throw new IllegalStateException("자동가입방지 코드 오류");
            }

            if(boardService.newBoard(newBoardDTO)){
                response = ResponseEntity.ok().build();
            }

        }catch (IllegalStateException ex){
            response=ResponseEntity.badRequest().body(ex.getMessage());
        }

        return response;
    }


    @PostMapping("/reply")
    public String replyok(NewReplyDTO newreplyDTO){
        String returnPage = "redirect:/board/view?bno=" + newreplyDTO.getPno();

        if(!boardService.newReply(newreplyDTO)){
            returnPage = "redirect:/board/error?type=1";
        }
        return returnPage;
    }

    @PostMapping("/cmmt")
    public String cmmtok(NewReplyDTO newreplyDTO){
        String returnPage = "redirect:/board/view?bno=" + newreplyDTO.getPno();
        if(!boardService.newComment(newreplyDTO)){
            returnPage = "redirect:/board/error?type=1";
        }
        return returnPage;
    }
}
