import 'package:day06/day06.dart' show MemoryManager;

main(List<String> arguments){
  List input = [0,5,10,0,11,14,13,4,11,8,8,7,1,4,12,11];
  MemoryManager mem = new MemoryManager(input);
  print("result for $input is ${mem.redistribution()}");
}