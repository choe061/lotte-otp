OTP 모듈 개발
===================
#### 2차 인증 OTP 모듈 개발 
* 애플리케이션 배포 스크립트 - deploy.sh
    ```
    #!/bin/bash
    REPOSITORY=/home/ec2-user/lotte-otp
    cd $REPOSITORY/lotte-otp
    echo "> Git Pull"
    sudo git pull
    echo "> 프로젝트 Build 시작"
    sudo ./gradlew build
    echo "> Build 파일 복사"
    sudo cp ./build/libs/*.jar $REPOSITORY/
    echo "> 현재 구동중인 애플리케이션 pid 확인"
    CURRENT_PID=$(pgrep -f otp)
    echo "$CURRENT_PID"
    if [ -z $CURRENT_PID ]; then
        echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
    else
        echo "> kill -2 $CURRENT_PID"
        sudo kill -9 $CURRENT_PID
        sleep 5
    fi
    echo "> 새 애플리케이션을 배포합니다"
    JAR_NAME=$(ls $REPOSITORY/ |grep 'otp' | tail -n 1)
    echo "> JAR Name: $JAR_NAME"
    sudo nohup java -jar $REPOSITORY/$JAR_NAME &
    ```