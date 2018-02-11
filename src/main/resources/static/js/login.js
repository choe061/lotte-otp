var isCheckInputBox = function (id, pw) {
    var idPattern = new RegExp("^[a-zA-Z][a-zA-Z0-9]{5,19}$");
    var pwPattern = new RegExp("^[a-zA-Z0-9]{5,29}$");
    if (idPattern.test(id) && pwPattern.test(pw)) {
        return true;
    }
    return false;
};

var saveCookieChanges = function () {
    var checked = $('#save-id').is(':checked');
    if (checked) {
        var id = $('#id').val();
        Cookies.set('id', id, { expires: 7 });  //Save in Cookie for 7 days
    } else {
        Cookies.remove('id');
    }
};

var requestLogin = function () {
    saveCookieChanges();
    var id = $('#id').val();
    var pw = $('#pw').val();
    if (isCheckInputBox(id, pw)) {
        $.ajax({
            url: "/user/login",
            method: "POST",
            contentType : "application/json; charset=UTF-8",
            dataType: "json",
            data: JSON.stringify({
                "id": id,
                "pw": pw
            }),
            success: function (responseJson, status, xhr) {
                getOTPConnectStatus(id);
            },
            error: function (request, status, error) {
                alert("ID와 PW가 틀렸습니다.");
            }
        });
    } else {
        alert("ID, PW를 다시 입력하세요.");
    }
};
















var printLogo = function () {
    console.log(
        "%c   __       _____  __________  __________  ___________           \
        \n%c  | |      /     \\ |___   ___| |___   ___||   ________|        _______    ___                 \
        \n%c  | |      |  _  |    |  |        |  |    |  |_______      _  / _____/  /  _ \\                 \
        \n%c  | |      | (_) |    |  |        |  |    |   _______|    (_)( (_____   | (_) |           \
        \n%c  | |____  |     |    |  |        |  |    |  |________        \\______\\   \\___/                \
        \n%c  |______| \\_____/    |__|        |__|    |___________|  "
        //
        // "%c   ____                                    \
        // \n%c  / ___|___  _   _ %c_ __   %c__ _ %c_ __   %c__ _ \
        // \n%c | |   / _ \\| | | %c| '_ \\ %c/ _` %c| '_ \\ %c/ _` | \
        // \n%c | |__| (_) | |_| %c| |_) %c| (_| %c| | | | %c(_| | \
        // \n%c  \\____\\___/ \\__,_%c| .__/ %c\\__,_%c|_| |_|%c\\__, | \
        // \n%c                  %c|_|                %c|___/ \
        // \n      %cLife is too short, you need cou%cp%ca%cn%cg.\n",
        // color.brown,
        // color.brown, color.red, color.yellow, color.green, color.blue,
        // color.brown, color.red, color.yellow, color.green, color.blue,
        // color.brown, color.red, color.yellow, color.green, color.blue,
        // color.brown, color.red, color.yellow, color.green, color.blue,
        // color.brown, color.red, color.blue,
        // color.brown, color.red, color.yellow, color.green, color.blue
    );
};