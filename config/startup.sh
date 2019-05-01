pid=`ps -ef|grep amebapay|awk '{print $2}'`
echo "pid=${pid}"
kill  $pid
nohup java -jar -Dlog.path=/home/logs/payment/pay amebapay.jar amebapay.jar --spring.profiles.active=prod &
