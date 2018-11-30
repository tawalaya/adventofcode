import 'package:day06/day06.dart';
import 'package:test/test.dart';

void main() {
  test('redistribution', () {
    MemoryManager m = new MemoryManager([0, 2, 7, 0]);
    expect(m.redistribution(), [5,4]);
  });
}
