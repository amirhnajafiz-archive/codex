# CodeX Golang Example

<br />

Let's make http requests into CodeX api to see how we can work with CodeX.
CodeX is a free api for executing codes. It provides two apis for getting
the list of available programming languages and executing a code.

<br />

## API

In order to execute a code, you need to send a ```post``` request to CodeX api.

- URL: ```https://api.codex.jaagrav.in```
- Method: ```POST```
- Body: ```query string```

### CURL

```shell
curl -X POST -H 'Content-type: application/x-www-form-urlencoded' -d 'query-string' https://api.codex.jaagrav.in
```

<br />

## Query String

A query string is a set of characters tacked onto the end of a URL. 
The query string begins after the question mark (?) and can include 
one or more parameters. Each parameter is represented by a unique 
key-value pair or a set of two linked data items. An equals sign 
(=) separates each key and value.

### Example

```
?foo=unicorn&ilike=pizza
```

### CodeX QS

For your request body you should convert the following data to a query string.

#### Input

```json
{
  "code": "print(int(input())+7)",
  "language": "py",
  "input": "5",
}
```

#### Convert to

```
code=value+%3D+int%28input%28%29%29+%2B+7%0Aprint%28value%29&input=5&language=py
```

#### JSON Response

```json
{
  "timeStamp": 1676969214607,
  "status": 200,
  "output": "12\n",
  "error": "",
  "language": "py",
  "info": "Python 3.6.9\n"
}
```

<br />

## Test

See the test example implemented in Golang.

### Build

```shell
go build -o main
```

### Run

```shell
./main -f example.py -e py -i 8
```

#### Flags

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
