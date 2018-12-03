import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Kruskals {
    private static final String filePath = "assn9_data.csv";

    private Kruskals() {
        numVertices = 0;
        cityToNum = 0;
        citiesToDistance = new HashMap<>();
        cities = new HashMap<>();
        pq = new PriorityQueue<>(Comparator.comparingInt(HashMap.Entry<String[], Integer> :: getValue));
    }

    private void readCSV(String path) {
        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                String start = data[0];
                int thisMaxLength = 0;
                if (!cities.containsKey(start)) {
                    cities.put(start, cityToNum);
                    cityToNum++;
                    numVertices++;
                }
                for (int i = 1; i < data.length; i += 2) {
                    String end = data[i];
                    if (!(cities.containsKey(end))) {
                        cities.put(end, cityToNum);
                        thisMaxLength = start.length() + end.length();
                        if (thisMaxLength > maxLength) maxLength = thisMaxLength;
                        cityToNum++;
                        numVertices++;
                    }
                    int distance = Integer.parseInt(data[i + 1]);
                    String[] edge = new String[2];
                    if (start.compareTo(end) < 0) {
                        edge[0] = start;
                        edge[1] = end;
                    } else {
                        edge[0] = end;
                        edge[1] = start;
                    }
                    if (!(citiesToDistance.containsKey(edge))) {
                        citiesToDistance.put(edge, distance);
                    }
                    for(HashMap.Entry<String[], Integer> entry: citiesToDistance.entrySet())
                    {
                        pq.offer(entry);
                    }
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main( String [ ] args ) {
        Kruskals obj = new Kruskals();

        obj.readCSV(filePath);

        DisjSets ds = new DisjSets(numVertices);
        List<String[]> mst = new ArrayList<>();
        int sumDistance = 0;
        while (mst.size() != numVertices - 1) {
            if (!(pq.isEmpty())) {
                String[] e = pq.poll().getKey();
                if (ds.find(cities.get(e[0])) != ds.find(cities.get(e[1]))) {
                    mst.add(e);
                    ds.union(ds.find(cities.get(e[0])), ds.find(cities.get(e[1])));
                }
            }
        }
        for (String[] e : mst) {
            sumDistance += citiesToDistance.get(e);
            StringBuilder space = new StringBuilder();
            for (int i = 0;i < 4 + maxLength + 3 - Arrays.toString(e).length(); i++) {
                space.append(" ");
            }
            System.out.println("edge : " + Arrays.toString(e) + new String(space) +"distance : " + citiesToDistance.get(e));
        }
        System.out.println();
        System.out.println("The sum of all of the distances in the tree is " + sumDistance + ".");
    }

    private static int numVertices;
    private static int cityToNum;
    private static HashMap<String[], Integer> citiesToDistance;
    private static HashMap<String, Integer> cities;
    private static PriorityQueue<HashMap.Entry<String[], Integer>> pq;
    private static int maxLength;
}

class DisjSets
{
    public DisjSets( int numElements )
    {
        s = new int [ numElements ];
        for( int i = 0; i < s.length; i++ )
            s[ i ] = -1;
    }

    public void union( int root1, int root2 )
    {
        if( s[ root2 ] < s[ root1 ] )
            s[ root1 ] = root2;
        else
        {
            if( s[ root1 ] == s[ root2 ] )
                s[ root1 ]--;
            s[ root2 ] = root1;
        }
    }

    public int find( int x )
    {
        if( s[ x ] < 0 )
            return x;
        else
            return s[ x ] = find( s[ x ] );
    }

    public int [ ] s;
}