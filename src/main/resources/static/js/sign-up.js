/**
 * 회원가입 시 아이디 중복체크 기능
 * @param id
 * 에러 처리 생각하기
 */
var duplicateId = function (id) {
    if (isValidId(id)) {
        $.ajax({
            url: "/user/duplicate/" + id,
            method: "GET",
            type: "json",
            success: function (data) {
                alert("사용 가능한 아이디 입니다.");
                console.log("%cSuccess%c!!!", "color: blue", "color: red");
            },
            error: function (xhr, ajaxOptions, error) {
                if (xhr.status === 409) {
                    alert("이미 존재하는 아이디입니다!")
                } else {
                    alert("다시 시도해주세요.");
                    console.log("%cError%c!!!", "color: blue", "color: red");
                }
            }
        });
    } else {
        alert("아이디를 다시 입력하세요. (영문,숫자 조합 5자 이상, 20자 이하)")
    }
};

var isValidId = function (id) {
    var pattern = new RegExp("^[a-zA-Z][a-zA-Z0-9]{4,20}$");    //ID의 첫번째 글자는 반드시 영문으로 시작 이후는 영문+숫자 조합
    return pattern.test(id);
};