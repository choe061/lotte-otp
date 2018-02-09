
var getOTPConnectStatus = function (id) {
    $.ajax({
        url: "/otp/connect/"+id,
        method: "GET",
        contentType : "application/json; charset=UTF-8",
        dataType: "json",
        success: function (data, status, xhr) {
            if (xhr.status === 200) {           //OTP 연동 회원
                $('#otp-input-dialog').modal('show');
                $('#otp-id').val($('#id').val());
            } else if (xhr.status === 203) {    //OTP 미연동 회원
                $('#otp-connect-dialog').modal('show');
                $('#temp-key').text(data.temp_key);
            }
        },
        error: function (request, status, error) {
            alert("OTP 오류 발생");
        }
    });
};

var isCheckOTP = function (otp) {
    var otpPattern = new RegExp("^[0-9]{6}$");
    if (otpPattern.test(otp)) {
        return true;
    }
    return false;
};

var request2NdAuth = function (otp) {
    $.ajax({
        url: "/otp/auth/"+otp,
        method: "GET",
        contentType : "application/json; charset=UTF-8",
        dataType: "json",
        success: function (data, status, xhr) {
            if (xhr.status === 200) {
                window.location.href = "main";
            }
        },
        error: function (xhr, ajaxOptions, error) {
            var json = $.parseJSON(xhr.responseText);
            if (xhr.status === 401 || xhr.status === 406) {
                alert(json.reason);
            }
            console.log("status : " + xhr.status + ", "+json.reason);
        }
    });
};