#! /bin/env python3

import editdistance

def read(filename):
    with open(filename,'r') as f:
        for line in f:
            yield line.strip()

def checksum(ids):
    two = 0
    three = 0
    for id in ids:
        letters = {}
        for c in id:
            if c in letters:
                letters[c] +=1
            else :
                letters[c] = 1
        _two = False
        _three = False
        for _,v in letters.items():
            if v == 3:
                _three = True
            if v == 2:
                _two = True
        if _two:
            two +=1
        if _three:
            three +=1

    return two*three

def findPair(ids):
    #persist list
    ids = list(ids)
    dists = {}
    for id in ids:
        closes = None
        minDist = 2<<31
        for _id in ids:
            if id != _id:
                d = editdistance.eval(id,_id)
                if d < minDist:
                    minDist = d
                    closes = _id
        dists[id] = (closes,minDist)
    
    for k,v in dists.items():
        if v[1] == 1:
            return (k,v[0])
    
    return None

def solution(pair):
    word = []
    for i in range(len(pair[0])):
        if pair[0][i] == pair[1][i]:
            word.append(pair[0][i])
    return ''.join(word) 

def main(filename = "input.txt"):
    sum = checksum(read(filename))

    print("checksum is:",sum)

    pair = findPair(read(filename))

    print("part II",solution(pair))
    

if __name__ == "__main__":
    main()