
var getOTPConnectStatus = function (id) {
    $.ajax({
        url: "/otp/connect/"+id,
        method: "GET",
        contentType : "application/json; charset=UTF-8",
        dataType: "json",
        success: function (data, status, xhr) {
            if (xhr.status === 200) {           //OTP 연동 회원
                $('#myModal').modal('show');
            } else if (xhr.status === 203) {    //OTP 미연동 회원
                $('#otpConnectDialog').modal('show');
                $('#temp-key').text(data.temp_key);
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