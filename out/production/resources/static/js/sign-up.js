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
                console.log(data.result);
                alert(data.result);
                console.log("%cSuccess%c!!!", "color: blue", "color: red");
            },
            error: function (request, status, error) {
                if (status === 409) {
                    alert("이미 존재하는 아이디입니다.")
                } else {
                    alert("error");
                    console.log("%cError%c!!!", "color: blue", "color: red");
                }
            }
        });
    } else {
        alert("아이디를 다시 입력하세요. (영문,숫자 조합 6자 이상, 20자 이하)")
    }
};

var isValidId = function (id) {
    var pattern = new RegExp("^[a-zA-Z][a-zA-Z0-9]{5,19}$");
    return pattern.test(id);
};

var showOtpDialog = function (id) {
    $.ajax({
        url: "/to-do/"+id,
        method: "GET",
        type: "json",
        success: function(data) {
            console.log(data.title);
            $("#myModal").modal("show");
            $("#m-title").val(data.title);
            $("#m-detail").val(data.detail);
            $("#m-updated-at").val(data.updated_at);
            $("#m-location").val(data.location);
        }, error: function(req, status, error) {
            console.log(error+", "+id);
        }
    });
}