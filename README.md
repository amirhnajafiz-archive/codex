# CodeX Golang Example

<br />

Let's make a http request into CodeX api to see how we can work with CodeX.

## Test

### Build

```shell
go build -o main
```

### Run

```shell
./main -f example.py -e py -i 8
```

<br />

## Flags

|   Flag   | Description    | Example       |
|:--------:|----------------|---------------|
| ```-f``` | Input file     | ```main.py``` |
| ```-e``` | Extension type | ```py```      |
| ```-i``` | File inputs    | ```5```       |

<br />

## HTTP

```shell
POST / HTTP/1.1
Host: api.codex.jaagrav.in
Content-Type: application/x-www-form-urlencoded

code=value+%3D+int%28input%28%29%29+%2B+7%0Aprint%28value%29&input=5&language=py
```

```shell
HTTP/2.0 200 OK
Access-Control-Allow-Origin: *
Alt-Svc: h3=":443"; ma=86400, h3-29=":443"; ma=86400
Cf-Cache-Status: DYNAMIC
Cf-Ray: 79ce36d41dc27795-LHR
Content-Type: application/json; charset=utf-8
Date: Tue, 21 Feb 2023 08:46:54 GMT
Etag: W/"6b-jDtXneqHbGJjvGaruweLedLANso"
Server: cloudflare
Vary: Accept-Encoding
X-Powered-By: Express
X-Render-Origin-Server: Render

{"timeStamp":1676969214607,"status":200,"output":"12\n","error":"","language":"py","info":"Python 3.6.9\n"}
```
