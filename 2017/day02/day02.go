package main

import (
	"fmt"
	"strconv"
	"strings"
)

func main() {
	var input = `
	`
	fmt.Printf("%d\n", star3(input))
	fmt.Printf("%d\n", star4(input))

}

func star4(input string) int64 {

	var lines = strings.Split(input, "\n")
	var checksum int64
	//split each line
	for _, line := range lines {
		var numbers = strings.Fields(line)
		var i int64
		var ints = make([]int64, len(numbers))
		//parse ints to array
		for idx := 0; idx < len(numbers); idx++ {

			i, _ = strconv.ParseInt(strings.TrimSpace(numbers[idx]), 10, 64)
			ints[idx] = i
		}

		//find evenly devided ints and add to checksum
		for x := 0; x < len(ints); x++ {
			for y := 0; y < len(ints); y++ {
				if x != y {
					if ints[x]%ints[y] == 0 {

						checksum += ints[x] / ints[y]
						break
					}
				}
			}
		}
		//XXX should ints be cleaned up?
	}
	return checksum
}

func star3(input string) int64 {
	var lines = strings.Split(input, "\n")
	//fmt.Printf("lines:%d\n", len(lines))
	var checksum int64
	//split lines
	for _, line := range lines {
		var numbers = strings.Fields(line)
		var max int64 = -1
		var min int64 = 2 << 32 //just some big number :)
		var i int64
		//parse ints and find min & max
		for _, number := range numbers {

			i, _ = strconv.ParseInt(strings.TrimSpace(number), 10, 64)

			if i < min {
				min = i
			}
			if i > max {
				max = i
			}
		}

		checksum += (max - min)
		//fmt.Printf("[debug]:numbers:%d min:%d max:%d check:%d\n", len(numbers), min, max, checksum)
	}
	return checksum
}
