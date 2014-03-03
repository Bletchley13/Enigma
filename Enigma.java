import java.io.*;
import java.util.*;

public class Enigma {
	static char map[][];
    static char rmap[][];
	public static void setmap() {
		try {
			map = new char[4][26];
            rmap =new char[3][26];
			Scanner in = new Scanner(new File("key.txt"));

			for (int i = 0; i < 3; i++) {
				String str = in.next();
				for (int j = 0; j < 26; j++)
				{
					char tmp = str.charAt(j);
					map[i][j] = tmp;
					rmap[i][tmp-'a']=(char)(j+'a');
				}
			}
			for (int i = 0; i < 13; i++) {
				String str = in.next();
				map[3][str.charAt(0) - 'a'] = str.charAt(1);
				map[3][str.charAt(1) - 'a'] = str.charAt(0);
			}

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 26; j++)
					System.out.print(map[i][j]);
				System.out.println();
			}
			System.out.println();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 26; j++)
					System.out.print(rmap[i][j]);
				System.out.println();
			}
			in.close();
		} catch (FileNotFoundException f) {
			System.out.println("key file noe found");
		}

	}

	public static void main(String argv[]) {
		try {
			setmap();
//			Scanner in = new Scanner(new File("in.txt"));
//			String str=in.next();
//			in.close();
			FileReader in = new FileReader("message.txt");
			FileWriter out = new FileWriter("codedmessage.txt");
			char tmp;
			int i=1;
			while((tmp=(char)in.read())>='a' && tmp<='z') {
				tmp = enit(map[0], i, 0, tmp);
				tmp = enit(map[1], i, 1, tmp);
				tmp = enit(map[2], i, 2, tmp);
				tmp = map[3][tmp-'a'];
				tmp = renit(rmap[2], i, 2, tmp);
				tmp = renit(rmap[1], i, 1, tmp);
				tmp = renit(rmap[0], i, 0, tmp);
				out.write(tmp);
				i++;
			}
			System.out.println("finish");
			in.close();
            out.close();
		} catch (FileNotFoundException p) {
		} catch (IOException e) {
		}

	}

	public static char enit(char str[], int round, int num, char tmp) {
		int i = tmp - 'a';
		int ror = (int) (round / Math.pow(26, num));
		return (char) (str[(i + ror + 26) % 26]);
	}

	public static char renit(char str[], int round, int num, char tmp) {
		int i = tmp - 'a';
		int ror = (int) (round / Math.pow(26, num))%26;
		return (char) ((str[i] - 'a' - ror + 26) % 26 + 'a');
	}

}
