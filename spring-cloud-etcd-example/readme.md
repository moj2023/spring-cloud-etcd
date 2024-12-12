## check

```
etcdctl --user=username:password  get --prefix ""
/services/application/172.2.0.194:8080
{"serviceName":"application","address":"172.2.0.194","port":8080,"serviceId":"application"}


# 指定prefix后
/springcloud/application/172.2.0.194:8080
{"serviceName":"application","address":"172.2.0.194","port":8080,"serviceId":"application"}

```