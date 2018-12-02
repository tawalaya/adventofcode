package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

func main() {

	sum := 0
	sums := make([]int, 0)

	repeat := true //Set to false for Part I
	for repeat {
		file, err := os.Open("input.txt")
		if err != nil {
			log.Fatal("failed to read - ", err)
		}

		scanner := bufio.NewScanner(file)
		for scanner.Scan() {

			text := scanner.Text()

			i, err := strconv.Atoi(text)
			if err != nil {
				log.Fatal("Faied to read ", text)
			}

			sum += i

			if contains(sums, sum) {
				log.Printf("Part II Result :%d\n", sum)
				repeat = false
				return
			}

			sums = append(sums, sum)
		}
		file.Close()
		fmt.Printf("%d\r", len(sums))
	}

	fmt.Printf("Part I Result :%d\n", sum)
}

func contains(s []int, e int) bool {
	for _, a := range s {
		if a == e {
			return true
		}
	}
	return false
}
