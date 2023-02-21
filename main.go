package main

import (
	"bytes"
	"encoding/json"
	"flag"
	"fmt"
	"io"
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"
	"os"
	"reflect"
)

const (
	apiUrl = "https://api.codex.jaagrav.in"
)

func read(path string) (string, error) {
	b, err := os.ReadFile(path)
	if err != nil {
		return "", err
	}

	return string(b), nil
}

func main() {
	// program flags
	var (
		FilePath  = flag.String("f", "", "input file")
		Extension = flag.String("e", "", "code programming language")
		Input     = flag.String("i", "", "code input")
	)

	flag.Parse()

	// get input file
	content, err := read(*FilePath)
	if err != nil {
		panic(err)
	}

	// create payload content
	queryParams := url.Values{
		"code":     {content},
		"language": {*Extension},
		"input":    {*Input},
	}

	payload := bytes.NewReader([]byte(queryParams.Encode()))
	client := http.Client{}

	// check the query string
	log.Printf("query string:\n%s\n\n", queryParams.Encode())

	// create http request
	req, _ := http.NewRequest(http.MethodPost, apiUrl, payload)
	req.Header.Set("Content-type", "application/x-www-form-urlencoded")

	// check the request
	reqDump, _ := httputil.DumpRequest(req, true)
	log.Printf("request:\n%s\n\n", reqDump)

	resp, err := client.Do(req)
	if err != nil {
		panic(err)
	}

	// check the response
	respDump, _ := httputil.DumpResponse(resp, true)
	log.Printf("response:\n%s\n\n", respDump)

	// create response struct
	type Response struct {
		TimeStamp int    `json:"timeStamp"`
		Status    int    `json:"status"`
		Output    string `json:"output"`
		Error     string `json:"error"`
		Language  string `json:"language"`
		Info      string `json:"info"`
	}

	// get response
	var r Response

	bytesString, _ := io.ReadAll(resp.Body)
	if err := json.Unmarshal(bytesString, &r); err != nil {
		panic(err)
	}

	fmt.Println("fields:")

	t := reflect.TypeOf(r)
	for i := 0; i < t.NumField(); i++ {
		f := t.Field(i)

		fmt.Printf("%s: %s\n", f.Name, f.Type)
	}

	fmt.Printf("\noutput:\n")

	if r.Error == "" {
		log.Println(r.Output)
	} else {
		log.Println(r.Error)
	}
}
