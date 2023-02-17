package main

import (
	"bytes"
	"io"
	"log"
	"net/http"
	"net/url"
)

const (
	url2 = "https://api.codex.jaagrav.in"
)

func main() {
	queryParams := url.Values{
		"code":     {"val = int(input()) + 5\nprint(val)"},
		"language": {"py"},
		"input":    {"7"},
	}

	client := http.Client{}

	address := url2 + "?" + queryParams.Encode()
	log.Println(address)

	req, _ := http.NewRequest(http.MethodPost, url2, bytes.NewReader([]byte(queryParams.Encode())))
	req.Header.Set("Content-type", "application/x-www-form-urlencoded")

	resp, err := client.Do(req)
	if err != nil {
		panic(err)
	}

	be, _ := io.ReadAll(resp.Body)

	log.Println(string(be))
}
