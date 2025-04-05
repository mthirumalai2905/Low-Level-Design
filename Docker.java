import java.io.*;
import java.util.*;

class MiniDocker {

    class Img {
        String n;
        List<String> ins;

        Img(String n, List<String> ins) {
            this.n = n;
            this.ins = ins;
        }
    }

    class Ctr {
        String id;
        Img img;
        Process p;

        Ctr(String id, Img img) {
            this.id = id;
            this.img = img;
        }

        void start() throws IOException {
            System.out.println("Starting " + id);
            for (String cmd : img.ins) {
                ProcessBuilder pb = new ProcessBuilder(cmd.split(" "));
                pb.inheritIO();
                p = pb.start();
            }
        }
    }

    class Engine {
        Map<String, Img> imgs = new HashMap<>();
        Map<String, Ctr> ctrs = new HashMap<>();

        void build(String name, File file) throws IOException {
            List<String> ins = new ArrayList<>();
            BufferedReader r = new BufferedReader(new FileReader(file));
            String l;
            while ((l = r.readLine()) != null) {
                if (!l.startsWith("#") && !l.trim().isEmpty()) {
                    ins.add(l.trim());
                }
            }
            imgs.put(name, new Img(name, ins));
            System.out.println("Built: " + name);
        }

        void run(String name) throws IOException {
            Img i = imgs.get(name);
            if (i == null) {
                System.out.println("Not found");
                return;
            }
            String id = UUID.randomUUID().toString().substring(0, 8);
            Ctr c = new Ctr(id, i);
            ctrs.put(id, c);
            c.start();
        }
    }

    public static void main(String[] args) {
        MiniDocker m = new MiniDocker();
        Engine e = m.new Engine();
        try {
            File f = new File("Dockerfile");
            e.build("img", f);
            e.run("img");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
