const boardMessages = [
    '제목을 올바르게 입력하세요',
    '본문내용을 올바르게 입력하세요',
    '자동가입방지를 올바르게 클릭하세요',
];

function clearErrorMessages() {
    document.querySelectorAll(".error-message")
        .forEach(error => error.textContent = '');
}

const validBoard = (form) => {
    let isValid = true;
    // 로그인 폼안의 모든 input 요소 수집
    const inputs = form.querySelectorAll('input:not([readonly]), textarea:not([readonly])');

    inputs.forEach((input, idx) => {    // input 요소를 하나씩 검사
        if (!input.checkValidity()) { // html5 태그를 이용한 유효성 검사
            displayErrorMessages(input , boardMessages[idx]);
            isValid = false;
        }
    });
    const ggrecaptcha = grecaptcha.getResponse();
    if(ggrecaptcha === ""){
        const recaptcha = document.querySelector('#recaptcha');
        displayErrorMessages(recaptcha,boardMessages[2]);
        isValid = false;
    }
    console.log(ggrecaptcha);

    return isValid;
}

const submitBoardfrm = async (frm) => {
    // 폼에 입력된 데이터를 formData 객체로 초기화
    const formData = new FormData(frm);

    fetch('/board/write', {
        method: 'POST',
        body: formData
    }).then(async response => {
        if (response.ok) {
            alert('게시글 등록 성공');
            location.href = '/board/list';
        } else if (response.status === 400) {
            alert(await response.text());
        } else {
            alert('게시글 등록 실패');

        }
    }).catch(error => {
        console.error('board write error', error);
        alert('서버와 통신중 오류 발생');
    });
}

const displayErrorMessages = (input, message) => {
    let error = document.createElement('div');
    error.className = 'error-message';
    error.textContent = message;
    input.parentElement.appendChild(error);
}

