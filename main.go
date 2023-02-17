package main

import (
	"bytes"
	"io"
	"log"
	"net/http"
	"net/url"
)

const (
	apiUrl = "https://api.codex.jaagrav.in"
)

func main() {
	queryParams := url.Values{
		"code":     {"val = int(input()) + 5\nprint(val)"},
		"language": {"py"},
		"input":    {"7"},
	}

	payload := bytes.NewReader([]byte(queryParams.Encode()))
	client := http.Client{}

	req, _ := http.NewRequest(http.MethodPost, apiUrl, payload)
	req.Header.Set("Content-type", "application/x-www-form-urlencoded")

	resp, err := client.Do(req)
	if err != nil {
		panic(err)
	}

	responseString, _ := io.ReadAll(resp.Body)

	log.Println(string(responseString))
}
