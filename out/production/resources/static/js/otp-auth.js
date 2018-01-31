
var getOTPConnectStatus = function (id) {
    $.ajax({
        url: "/otp/connect/" + id,
        method: "GET",
        dataType: "json",
        success: function (responseJson, status, xhr) {
            console.log(xhr.status);
            if (xhr.status === 200) {           //OTP 연동 회원
                $('#myModal').modal('show');
            } else if (xhr.status === 204) {    //OTP 미연동 회원
                $('#otpConnectDialog').modal('show');
            }
        },
        error: function (request, status, error) {
            alert("error : "+status+". "+error);
        }
    });
};

var showOtpInputDialog = function () {

};

var showOtpConnectDialog = function () {
    $('#otpConnectDialog').modal('show');
};