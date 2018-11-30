import 'dart:collection' show HashSet,HashMap;

class MemoryManager {
  List _banks;

  final HashSet _history = new HashSet();
  final HashMap _index = new HashMap();

  MemoryManager(this._banks);

  int getFor(List list)=>_index[fingerPrint(list)];

  List redistribution(){
    
    this._history.clear();
    this._index.clear();


    List memory = new List();
    memory.addAll(_banks);

    int jumps = 0;

    while(!_history.contains(fingerPrint(memory))){
       _history.add(fingerPrint(memory));
       
       _index[fingerPrint(memory)] = jumps;

       jumps++;
       int pos = findMax(memory);
       int blocks = memory[pos];
       memory[pos] = 0;

       pos = (pos+1)%memory.length;

       for(int i = 0;i<blocks;i++){
          memory[pos] += 1;
          pos = (pos+1)%memory.length;
       }

    }

    return [jumps,jumps-_index[fingerPrint(memory)]];
  }

  String fingerPrint(List memory) => memory.toString();

  int findMax(List memory) {
    int val = 0;
    for (var i = 0;i<memory.length;i++) {
      if(memory[val] < memory[i]){
        val = i;
      }
    }
    return val;
  }
}