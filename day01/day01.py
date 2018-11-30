def star1(l):
    r=[]
    for i in range(len(l)):
        if l[i] == l[(i+1)%len(l)]:
            r.append(int(l[i]))
    return sum(r)

def star2(l):
    r = []
    for i in range(len(l)):
        if l[i] == l[(i+(len(l)//2))%len(l)]:
             r.append(int(l[i]))
    return sum(r)