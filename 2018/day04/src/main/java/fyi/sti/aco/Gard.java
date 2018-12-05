public class Gard{
        int id;
        int[] shifts = new int[60];

        Gard(int id){
            this.id = id;
        }

        int asslip(){
            int sum = 0;
            for (int shift : shifts) {
                sum += shift;
            }
            return sum;
        }

        int mostLikely(){
            int max = -1;
            int maxI = 0;
            for (int i = 0; i < shifts.length; i++) {
                if(shifts[i] > max){
                    max = shifts[i];
                    maxI = i;
                }
            }
            return maxI;
        }

        int mostLikelyCount(){
            int max = -1;
            for (int i = 0; i < shifts.length; i++) {
                if(shifts[i] > max){
                    max = shifts[i];
                }
            }
            return max;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            sb.append(id);
            sb.append(" [");
            for (int i = 0; i < shifts.length; i++) {
                if (shifts[i] <= 0) {
                    sb.append(".");
                } else {
                    sb.append(shifts[i]);
                }

            }
            sb.append("] ");
            sb.append(asslip());
            sb.append(" - ");
            sb.append(mostLikely());
            sb.append(" - ");
            sb.append(mostLikelyCount());
            return sb.toString();
        }
    }