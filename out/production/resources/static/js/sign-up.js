/**
 * 회원가입 시 아이디 중복체크 기능
 * @param id
 */
var isValidId = function (id) {
    var pattern = new RegExp("^[a-zA-Z][a-zA-Z0-9]{5,19}$");
    return pattern.test(id);
};

var duplicateId = function (id) {
    var result = isValidId(id);
    console.log(result);
    if (result) {
        $.ajax({
            url: "/user/duplicate/" + id,
            method: "GET",
            type: "json",
            success: function (data) {
                console.log("%cSuccess%c!!!", "color: blue", "color: red");
                alert(data);
            },
            error: function (request, status, error) {
                console.log("%cError%c!!!", "color: blue", "color: red");
                alert("error");
            }
        });
    } else {
        alert("아이디를 다시 입력하세요. 영문,숫자 조합 6자 이상, 20자 이하")
    }
};