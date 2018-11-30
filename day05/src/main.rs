
use std::fs::File;
use std::io::BufReader;
use std::io::BufRead;

fn read_input() -> Vec<i32>{
     let f = File::open("input.txt").expect("file not found");

    let file = BufReader::new(&f);

    let mut vec: Vec<i32> = Vec::new();

    for line in file.lines() {
        let l = line.unwrap();
        vec.push(l.parse().unwrap());
    } 
    
    return vec;
}

fn star9(mut data: Vec<i32>) -> i32{
    let mut loc : usize = 0;
    let mut step_count = 0;
    loop {
        let jump_to  = data[loc] ;
        data[loc] +=1;
        loc = (jump_to + loc as i32) as usize;

        step_count+=1;

        if loc >= data.len() {
            break
        }
        
    }
    return step_count;
}

fn star10(mut data: Vec<i32>) -> i32{
    let mut loc : usize = 0;
    let mut step_count = 0;
    loop {
        let jump_to  = data[loc] ;
        if jump_to >= 3 {
            data[loc] -=1;
        } else {
            data[loc] +=1;
        }
        loc = (jump_to + loc as i32) as usize;

        step_count+=1;

        if loc >= data.len() {
            break
        }
        
    }
    return step_count;
}

fn main() {
    let data = read_input();
    print!("{}\n",star9(data.clone()));

    print!("{}\n",star10(data.clone()));
}