#! /usr/bin/env python

from multiprocessing import Pool

def testUpperLower(word,pos):
    return word[pos] != word[pos-1] and (word[pos].upper() == word[pos-1] or word[pos] == word[pos-1].upper())


def reduceUpperLower(word):
    return reduce(word,testUpperLower)

def reduce(word,tester):
    size = len(word)+1
    while len(word) < size:
        size = len(word)
        pos = 1
        while pos < len(word):
            if tester(word,pos):
                word = word[0:pos-1]+word[pos+1:]
            pos+=1
    return word

def chunk(in_string,num_chunks):
    chunk_size = len(in_string)//num_chunks
    if len(in_string) % num_chunks: chunk_size += 1
    iterator = iter(in_string)
    for _ in range(num_chunks):
        accumulator = list()
        for _ in range(chunk_size):
            try: accumulator.append(next(iterator))
            except StopIteration: break
        yield ''.join(accumulator)

def merge(chunks):
    for i in range(0,len(chunks),2):
        yield ''.join(chunks[i:i+2])

def reduceWord(word,reduce):
    processes = 2*4
    chunks = chunk(word,len(word)<<2)
    with Pool(processes) as p:
        while True:
            #fork
            chunks = p.map(reduce,chunks)
            if len(chunks) <= 1:
                break
            #merge
            chunks = merge(chunks)
        
    return len(chunks[0])

def main():
    word = None
    with open("input.txt","r") as f:
        word = f.readline()
    
    print("part I",reduceWord(word,reduceUpperLower))

    best = {}



    for char in 'abcdefghijklmnopqrstuvwxyz':
        #this part could prop be done faster?
        cWord = word.replace(char,'').replace(char.upper(),'')
        best[char] = reduceWord(cWord,reduceUpperLower)
    
    print(sorted(best.items(), key=lambda kv: kv[1]))


    
    

    
if __name__ == "__main__":
    main()
