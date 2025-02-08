import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Jan25Y2025 {
    /*
     * suppose i have a string abcdeccef
     * 
     */
    private static int getMinLenSubstring(String s) {
        HashSet<Character> hs = new HashSet<>();

        int si = 0, ei = s.length()-1;
        while ((si < ei) && (!hs.contains(s.charAt(ei)) || !hs.contains(s.charAt(si)))) {
            if(!hs.contains(s.charAt(si))) {
                hs.add(s.charAt(si++));
            }
            if(!hs.contains(s.charAt(ei))) {
                hs.add(s.charAt(ei--));
            }
        }

        return ei-si+1;
    }

    public int countPartitions(int[] nums) {
        int n = nums.length;
        int sum = 0; 
        for(int ele: nums) sum += ele;

        int currSum = 0, count = 0;
        for(int ele: nums) {
            currSum += ele;
            if((sum - currSum)%2 == 0) count++;
        }

        return count;
    }

    public int[] countMentions(int n, List<List<String>> events) {
        HashMap<Integer, Integer> offlineTime = new HashMap<>();
        int[] mentioned = new int[n]; 
        
        Collections.sort(events, (a, b) -> {
            if(Integer.parseInt(a.get(1)) != Integer.parseInt(b.get(1))) Integer.parseInt(a.get(1)) - Integer.parseInt(b.get(1));
            return -1;
        });

        for(int i = 0; i < events.size(); i++) {
            String message = events.get(i).get(0);
            String timeStamp = events.get(i).get(1);
            int eventTimeStamp = Integer.parseInt(timeStamp);

            String mentionString = events.get(i).get(2);

            if(message.equals("MESSAGE")) {
                if(mentionString.equals("HERE")) {
                    for(int j = 0; j < n; j++) {
                        if(offlineTime.containsKey(j)) {
                            if(eventTimeStamp >= offlineTime.get(j))
                                mentioned[j]++;
                        }
                        else {
                            mentioned[j]++;
                        }
                    }
                }
                else if(mentionString.equals("ALL")) {
                    for(int j = 0; j < n; j++) {
                        mentioned[j]++;
                    }
                }
                else {
                    String[] userIds = mentionString.split(" ");
                    for(int j = 0; j < userIds.length; j++) {
                        int user = Integer.parseInt(userIds[j].substring(2));
                        mentioned[user]++;
                    }
                }
            }
            else {
                // user is going offline
                int offlineUserId = Integer.parseInt(mentionString);
                offlineTime.put(offlineUserId, eventTimeStamp+60);
            }
        }
        return mentioned;
    }

    public static void main(String[] args) {
        String str = "abccbbdeff";
        int ans = getMinLenSubstring(str);
        System.out.println("Hello world"+ ans);
    }
}
