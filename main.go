package main

import (
	"bytes"
	"encoding/json"
	"io"
	"log"
	"net/http"
)

type Object struct {
	Code     string `json:"code"`
	Language string `json:"language"`
	Input    string `json:"input"`
}

const (
	url = "https://api.codex.jaagrav.in"
)

func main() {
	obj := Object{
		Code:     "val = int(input('Enter your value: ')) + 5\nprint(val)",
		Language: "py",
		Input:    "7",
	}

	b, _ := json.Marshal(&obj)

	client := http.Client{}

	req, _ := http.NewRequest(http.MethodPost, url, bytes.NewReader(b))
	req.Header.Set("Content-type", "application/x-www-form-urlencoded")

	resp, err := client.Do(req)
	if err != nil {
		panic(err)
	}

	be, _ := io.ReadAll(resp.Body)

	log.Println(string(be))
}
