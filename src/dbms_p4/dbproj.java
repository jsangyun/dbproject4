package dbms_p4;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;


public class dbproj {
	
	static Scanner scan = new Scanner(System.in);
	
    static Statement st = null;
	static ResultSet rs = null;
	static ResultSet rs2 = null;
	static Connection conn;
	static ResultSetMetaData rsmd = null;
	
	static String managerMail; //로그인한 매니저 이름
	static String userId; //로그인한 유저 아이디
	
	public static void main(String[] args) {
		//Connect To mySQL (DB)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicapp?autoReconnect=true&useSSL=false", "root", "jsysoft16");
        	System.out.println("DB Connection OK!");
        }
        catch(ClassNotFoundException e) {
	       	e.printStackTrace();
	       	System.out.println("DB Driver Error!");
	      	
        }
	    catch(SQLException se) {
        	se.printStackTrace();
        	System.out.println("DB Connection Error!");
	    }
		MainMenu();
	}
	
	static int getUserNum() {
		int user_num = 0;
		try{
			st = conn.createStatement();
			rs = st.executeQuery("SELECT user_num FROM NUMBER;");
			if(rs.next()) {
				user_num = rs.getInt("user_num");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return user_num;
	}
	
	static int getMusicNum() {
		int music_num = 0;
		try{
			st = conn.createStatement();
			rs = st.executeQuery("SELECT music_num FROM NUMBER;");
			if(rs.next()) {
				music_num = rs.getInt("music_num");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return music_num;
	}
	
	static void MainMenu() {
		int input = 0;
		System.out.println("---------Main Menu---------");
		System.out.println("0.End Application\n1.User Menu\n2.Manager Menu\n3.Search Music");
		System.out.println("---------------------------");
		while(true) {
			System.out.println("Input:");
			input = scan.nextInt();
			switch(input) {
				case 0:
					System.out.println("Good Bye!");
					System.exit(0);
				case 1:
					UserMenu();
					break;
				case 2:
					ManagerMenu();
					break;
				case 3:
					SearchMusic();
					break;
				default:
					System.out.print("Invalid Input!\n");
			}
		}
	}
	static void SearchMusic() {
		String music_name;
		Scanner s = new Scanner(System.in);
		System.out.println("--------Search Music-------");
		System.out.println("Input Music Name:");
		music_name = s.nextLine();
		System.out.println("---------------------------");
		try {
			int resultNum = 0;
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM MUSIC WHERE m_title like '" + music_name + "';");
			while(rs.next()) {
				System.out.println("ID:" + rs.getString("m_id"));
				System.out.println("Title:" + rs.getString("m_title"));
				System.out.println("Artist:" + rs.getString("m_artist"));
				System.out.println("Playtime:" + rs.getString("m_playtime"));
				System.out.println("Lyric:" + rs.getString("m_lyric"));
				resultNum++;
			}
			System.out.println("---------------------------");
			System.out.println(resultNum + " Result(s) Found.");
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		MainMenu();
	}
	static void UserMenu() {
		int input = 0;
		System.out.println("---------User Menu---------");
		System.out.println("0.Return to Main Menu\n1.Login\n2.Register");
		System.out.println("---------------------------");
		while(true) {
			System.out.println("Input:");
			input = scan.nextInt();
			switch(input) {
				case 0:
					MainMenu();
					break;
				case 1:
					UserLogin();
					break;
				case 2:
					UserRegister();
					break;
				default:
					System.out.print("Invalid Input!\n");
			}
		}
	}
	static void ManagerMenu() {
		int input = 0;
		System.out.println("-------Manager Menu--------");
		System.out.println("0.Return to Main Menu\n1.Login");
		System.out.println("---------------------------");
		while(true) {
			System.out.println("Input:");
			input = scan.nextInt();
			switch(input) {
				case 0:
					MainMenu();
					break;
				case 1:
					ManagerLogin();
					break;
				default:
					System.out.print("Invalid Input!\n");
			}
		}
	}
	
	static void UserLogin() {
		String user_id, user_pw;
		System.out.println("---------------------------");
		System.out.println("ID:");
		user_id = scan.next();
		System.out.println("Password:");
		user_pw = scan.next();
		System.out.println("---------------------------");
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM USER WHERE u_id = '" + user_id + "' and u_pw = '" + user_pw + "';");
			if(rs.next()) {
				userId = rs.getString("u_id");
				UserCommandMenu();
			}
			else {
				System.out.println("No such user account!");
				UserMenu();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void ManagerLogin() {
		String manager_email, manager_pw;
		System.out.println("---------------------------");
		System.out.println("Email:");
		manager_email = scan.next();
		System.out.println("Password:");
		manager_pw = scan.next();
		System.out.println("---------------------------");
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM MANAGER WHERE m_email= '" + manager_email + "' and m_pw= '" + manager_pw + "';");
			if(rs.next()) {
				managerMail = rs.getString("m_email");
				System.out.println("Welcome, Manager "+ rs.getString("m_name") + "!");
				ManagerCommandMenu();
			}
			else {
				System.out.println("No such manager account!");
				ManagerMenu();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	static void ManagerCommandMenu() {
		int input = 0;
		System.out.println("------Manager Command------");
		System.out.println("0.Logout\n1.Register New Manager\n2.Add Music\n3.Delete Music\n4.Update Music Info.\n5.Change Manager Info.\n6.Unregister");
		System.out.println("---------------------------");
		System.out.println("Input:");
		input = scan.nextInt();
		switch(input) {
			case 0:
				MainMenu();
				managerMail = null;
				break;
			case 1:
				ManagerRegister();
				break;
			case 2:
				ChangeMusic("add");
				break;
			case 3:
				ChangeMusic("delete");
				break;
			case 4:
				ChangeMusic("update");
				break;
			case 5:
				ChangeManagerInfo();
			case 6:
				ManagerUnregister();
		}
	}
	static void ChangeMusic(String what) {
		Scanner s = new Scanner(System.in);
		if(what.equals("add")) {
			String mtitle, martist, mplaytime, mlyric;
			int mid = getMusicNum();
			mid = mid + 1;
			System.out.println("---------Add Music---------");
			System.out.println("Title:");
			mtitle = s.nextLine();
			System.out.println("Artist:");
			martist = s.nextLine();
			System.out.println("Playtime:");
			mplaytime = s.nextLine();
			System.out.println("Lyric:");
			mlyric = s.nextLine();
			System.out.println("---------------------------");
			try {
				st = conn.createStatement();
				st.executeUpdate("INSERT INTO MUSIC VALUES('"+ mtitle + "','" + martist + "','" + mplaytime + "','" + mlyric + "','" + managerMail + "', default, default);");
				st.executeUpdate("UPDATE NUMBER SET music_num = '" + mid +"' WHERE n_id=0;");
				System.out.println("Music Add Completed!");
				ManagerCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
				mid = mid - 1;
			}
		}
		if(what.equals("delete")) {
			int mid;
			int m_num = getMusicNum();
			m_num = m_num -1;
			System.out.println("--------Delete Music-------");
			System.out.println("Input ID of Music:");
			mid = scan.nextInt();
			try{
				st = conn.createStatement();
				st.executeUpdate("DELETE FROM MUSIC WHERE m_id='" + mid + "';");
				st.executeUpdate("UPDATE NUMBER SET music_num = '" + m_num +"' WHERE n_id=0;");
				System.out.println("Deletion Completed");
				ManagerCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
				m_num = m_num + 1;
			}
			System.out.println("---------------------------");
		}
		if(what.equals("update")) {
			int mid;
			String mtitle, martist, mplaytime, mlyric;
			System.out.println("--------Update Music-------");
			System.out.println("Input ID of Music:");
			mid = scan.nextInt();
			System.out.println("Title:");
			mtitle = s.nextLine();
			System.out.println("Artist:");
			martist = s.nextLine();
			System.out.println("Playtime:");
			mplaytime = s.nextLine();
			System.out.println("Lyric:");
			mlyric = s.nextLine();
			System.out.println("---------------------------");
			try {
				st = conn.createStatement();
				st.executeUpdate("UPDATE MUSIC SET m_title = '"+ mtitle + "', m_artist='"+ martist +"', m_playtime='"+ mplaytime +"', m_lyric='"+ mlyric +"' WHERE m_id = " + mid + ";");
				System.out.println("Changed.");
				ManagerCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	static void UserRegister() {
		String user_id, user_email, user_birth, user_hpnum, user_pw, user_name, pw_check;
		int user_num = getUserNum();
		user_num = user_num + 1;
		System.out.println("---------------------------");
		System.out.println("Name:");
		user_name = scan.next();
		System.out.println("ID:");
		user_id = scan.next();
		System.out.println("Password:");
		user_pw = scan.next();
		System.out.println("Repeat Password:");
		pw_check = scan.next();
		System.out.println("E-mail:");
		user_email = scan.next();
		System.out.println("Birth Date:");
		user_birth = scan.next();
		System.out.println("HP:");
		user_hpnum = scan.next();
		System.out.println("---------------------------");
		if(user_pw.equals(pw_check)) {
			try {
				st = conn.createStatement();
				st.executeUpdate("INSERT INTO USER VALUES('" + user_hpnum + "','" + user_email + "','" + user_birth + "','" + user_name + "','" + user_id + "','" + user_pw + "');");
				st.executeUpdate("UPDATE NUMBER SET user_num = '" + user_num +"' WHERE n_id=0;");
				System.out.println("Registerd!");
				userId = user_id;
				UserCommandMenu();
			}
			catch(SQLException e) {
				System.out.println("Already existing keys.");
				user_num = user_num - 1;
				UserMenu();
			}
		}
		else {
			System.out.println("Password not matched. Try again");
			UserMenu();
		}
	}
	static void ManagerRegister() {
		String manager_email, manager_name;
		System.out.println("---------------------------");
		System.out.println("E-mail:");
		manager_email = scan.next();
		System.out.println("Name:");
		manager_name = scan.next();
		System.out.println("---------------------------");
		try {
			st = conn.createStatement();
			st.executeUpdate("INSERT INTO MANAGER VALUES('" + manager_email + "','" + manager_name + "',default);");
			System.out.println("Manager Registered!");
			managerMail = manager_email;
			ManagerCommandMenu();
		}
		catch(SQLException e) {
			System.out.println("Already existing e-mail.");
			ManagerCommandMenu();
		}
	}
	
	static void ManagerUnregister() {
		int input;
		System.out.println("Are you sure you want to UNREGISTER? 1.Yes 2.No");
		System.out.println("Input:");
		input = scan.nextInt();
		if(input == 1) {
			try {
				st = conn.createStatement();
				st.executeUpdate("DELETE FROM MANAGER WHERE m_email='" + managerMail + "';");
				managerMail = null;
				System.out.println("Unregisterd.");
				MainMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		else if(input == 2) {
			ManagerCommandMenu();
		}
		else {
			System.out.println("Invalid Input.");
			input = scan.nextInt();
		}
	}
	static void ChangeManagerInfo() {
		int input=0;
		System.out.println("---------------------------");
		System.out.println("0.Cancel\n1.Change E-mail\n2.Change Password\n3.Change Name");
		System.out.println("---------------------------");
		System.out.println("Input:");
		input = scan.nextInt();
		switch(input){
			case 0: 
				ManagerCommandMenu();
				break;
			case 1:
				ChangeManager("email");
				break;
			case 2:
				ChangeManager("password");
				break;
			case 3:
				ChangeManager("name");
				break;
		}
	}
	static void ChangeManager(String what) {
		String change;
		if(what.equals("email")) {
			System.out.println("--------Change Email-------");
			System.out.println("Input New E-mail:");
			change = scan.next();
			System.out.println("---------------------------");
			try {
				st = conn.createStatement();
				st.executeUpdate("UPDATE MANAGER SET m_email = '" + change + "' WHERE m_email = '" + managerMail + "';");
				System.out.println("Changed!");
				managerMail = change;
				ManagerCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(what.equals("password")) {
			String check;
			System.out.println("------Change Password------");
			System.out.println("Input New Password:");
			change = scan.next();
			System.out.println("Input New Password Again:");
			check = scan.next();
			System.out.println("---------------------------");
			if(check.equals(change)) {
				try {
					st = conn.createStatement();
					st.executeUpdate("UPDATE MANAGER SET m_pw = '" + change + "' WHERE m_email = '" + managerMail + "';");
					System.out.println("Changed!");
					managerMail = change;
					ManagerCommandMenu();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Password input wrong. Please try again.");
				ChangeManager("password");
			}
		}
		if(what.equals("name")) {
			System.out.println("--------Change Name-------");
			System.out.println("Input New Name:");
			change = scan.next();
			System.out.println("---------------------------");
			try {
				st = conn.createStatement();
				st.executeUpdate("UPDATE MANAGER SET m_name = '" + change + "' WHERE m_email = '" + managerMail + "';");
				System.out.println("Changed!");
				managerMail = change;
				ManagerCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	static void UserCommandMenu() {
		int input = 0;
		System.out.println("User ID: " + userId);
		System.out.println("---------User Menu---------");
		System.out.println("0.Logout\n1.List Playlists\n2.Add Playlist\n3.Delete Playlist\n4.Update Playlist\n5.Change User Info.\n6.Unregister");
		System.out.println("---------------------------");
		while(true) {
			System.out.println("Input:");
			input = scan.nextInt();
			switch(input) {
				case 0:
					MainMenu();
					userId = null;
					break;
				case 1:
					ChangePlayList("list");
					break;
				case 2:
					ChangePlayList("add");
					break;
				case 3: 
					ChangePlayList("delete");
					break;
				case 4:
					ChangePlayList("update");
					break;
				case 5: 
					ChangeUserInfo();
					break;
				case 6:
					UserUnregister();
					break;
				default:
					System.out.println("Invalid Input!");
					break;
			}
		}
	}
	static void ChangePlayList(String what) {
		Scanner s = new Scanner(System.in);
		if(what.equals("list")) {
			int playlistnum = 0;
			try {
				st = conn.createStatement();
				rs = st.executeQuery("SELECT * FROM PLAYLIST;");
				System.out.println("---------------------------");
				while(rs.next() && rs.getString("uid").equals(userId)) {
					System.out.println("Name:" + rs.getString("p_name"));
					System.out.println("ID:" + rs.getString("p_id"));
					playlistnum++;
				}
				System.out.println("---------------------------");
				System.out.println(playlistnum + " playlist(s) exists.");
				UserCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(what.equals("add")) {
			String name;
			System.out.println("-------Add Playlist--------");
			System.out.println("Playlist Name:");
			name = s.nextLine();
			System.out.println("---------------------------");
			try {
				st = conn.createStatement();
				st.executeUpdate("INSERT INTO PLAYLIST VALUES('" + name + "','" + userId + "', default);");
				System.out.println("Playlist Add Completed!");
				UserCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(what.equals("delete")) {
			int pid;
			System.out.println("-------Delete Playlist-----");
			System.out.println("Input ID of Playlist:");
			pid = scan.nextInt();
			try{
				st = conn.createStatement();
				st.executeUpdate("DELETE FROM PLAYLIST WHERE p_id='" + pid + "';");
				System.out.println("Deletion Completed");
				UserCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			System.out.println("---------------------------");
		}
		if(what.equals("update")) {
			int input=0;
			System.out.println("------Update Playlist------");
			System.out.println("0.Cancel\n1.Add Music\n2.Delete Music\n3.Change Name\n4.Show Musics");
			System.out.println("---------------------------");
			System.out.println("Input:");
			input = scan.nextInt();

			int musicid = 0, playlistid = 0;
			switch(input) {
				case 0:
					UserCommandMenu();
					break;
				case 1:
					System.out.println("Which Playlist?(Input ID):");
					playlistid = scan.nextInt();
					System.out.println("Which Music? (Input ID):");
					musicid = scan.nextInt();
					try {
						st = conn.createStatement();
						st.executeUpdate("INSERT INTO CONTAIN VALUES(" + playlistid + ", " + musicid + ");");
						System.out.println("Done");
						UserCommandMenu();
					}
					catch(SQLException e) {
						e.printStackTrace();
					}
					break;
				case 2:
					System.out.println("Which Playlist?(Input ID):");
					playlistid = scan.nextInt();
					System.out.println("Which Music? (Input ID):");
					try {
						st = conn.createStatement();
						st.executeUpdate("DELETE FROM CONTAIN WHERE pl_id=" + playlistid + ", mu_id=" + musicid + ";");
						System.out.println("Deleted");
						UserCommandMenu();
					}
					catch(SQLException e) {
						e.printStackTrace();
					}
					break;
				case 3:
					String newname;
					int plid = 0;
					System.out.println("Input Playlist ID:");
					plid = scan.nextInt();
					System.out.println("Input New Playlist Name:");
					newname = s.nextLine();
					try {
						st = conn.createStatement();
						st.executeUpdate("UPDATE PLAYLIST SET p_name = '" + newname + "' WHERE p_id='" + plid + "';");
						System.out.println("Changed!");
						UserCommandMenu();
					}
					catch(SQLException e) {
						e.printStackTrace();
					}
					break;
				case 4:
					System.out.println("Which Playlist? (Input ID):");
					playlistid = scan.nextInt();
					int i=0;
					int[] pmid = new int [100];
					try {
						st = conn.createStatement();
						rs = st.executeQuery("SELECT mu_id FROM CONTAIN WHERE pl_id = " + playlistid + ";");
						while(rs.next()) {
							pmid[i] = rs.getInt("mu_id");
							i++;
						}
						for(int j=0;j<i;j++) {
							rs = st.executeQuery("SELECT m_title FROM MUSIC WHERE m_id = " + pmid[j] + ";");
							while(rs.next()) {
								System.out.println("Title: " + rs.getString("m_title"));
							}
						}
					}
					catch(SQLException e) {
						e.printStackTrace();
					}
					UserCommandMenu();
					break;
			}
		}
	}
	static void ChangeUserInfo() {
		int input=0;
		System.out.println("---------------------------");
		System.out.println("0.Cancel\n1.Change ID\n2.Change Password\n3.Change Name\n4.Change HP Number\n5.Change Birth Date\n6.Change E-mail");
		System.out.println("---------------------------");
		System.out.println("Input:");
		input = scan.nextInt();
		switch(input){
			case 0: 
				UserCommandMenu();
				break;
			case 1:
				ChangeUser("id");
				break;
			case 2:
				ChangeUser("password");
				break;
			case 3:
				ChangeUser("name");
				break;
			case 4:
				ChangeUser("hp");
				break;
			case 5:
				ChangeUser("birth");
				break;
			case 6:
				ChangeUser("email");
				break;
		}
	}
	static void ChangeUser(String what){
		String change;
		if(what.equals("id")) {
			System.out.println("--------Change ID----------");
			System.out.println("Input New ID:");
			change = scan.next();
			System.out.println("---------------------------");
			try {
				st = conn.createStatement();
				st.executeUpdate("UPDATE USER SET u_id = '" + change + "' WHERE u_id = '" + userId + "';");
				userId = change;
				System.out.println("Changed!");
				UserCommandMenu();
			}
			catch(SQLException e) {
				System.out.println("Duplicated ID, please choose other ID.");
				ChangeUser("id");
			}
		}
		if(what.equals("birth")) {
			System.out.println("--------Change Birth-------");
			System.out.println("Input New Birth Date:");
			change = scan.next();
			System.out.println("---------------------------");
			try {
				st = conn.createStatement();
				st.executeUpdate("UPDATE USER SET u_birth = '" + change + "' WHERE u_id = '" + userId + "';");
				System.out.println("Changed!");
				UserCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(what.equals("hp")) {
			System.out.println("------Change HP Number-----");
			System.out.println("Input New Number:");
			change = scan.next();
			System.out.println("---------------------------");
			try {
				st = conn.createStatement();
				st.executeUpdate("UPDATE USER SET u_hpnumber = '" + change + "' WHERE u_id = '" + userId + "';");
				System.out.println("Changed!");
				UserCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(what.equals("email")) {
			System.out.println("--------Change Email-------");
			System.out.println("Input New E-mail:");
			change = scan.next();
			System.out.println("---------------------------");
			try {
				st = conn.createStatement();
				st.executeUpdate("UPDATE USER SET u_email = '" + change + "' WHERE u_id = '" + userId + "';");
				System.out.println("Changed!");
				UserCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(what.equals("password")) {
			String check;
			System.out.println("------Change Password------");
			System.out.println("Input New Password:");
			change = scan.next();
			System.out.println("Input New Password Again:");
			check = scan.next();
			System.out.println("---------------------------");
			if(check.equals(change)) {
				try {
					st = conn.createStatement();
					st.executeUpdate("UPDATE USER SET u_pw = '" + change + "' WHERE u_id = '" + userId + "';");
					System.out.println("Changed!");
					UserCommandMenu();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Password input wrong. Please try again.");
				ChangeManager("password");
			}
		}
		if(what.equals("name")) {
			System.out.println("--------Change Name-------");
			System.out.println("Input New Name:");
			change = scan.next();
			System.out.println("---------------------------");
			try {
				st = conn.createStatement();
				st.executeUpdate("UPDATE USER SET u_name = '" + change + "' WHERE u_id = '" + userId + "';");
				System.out.println("Changed!");
				UserCommandMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	static void UserUnregister() {
		int input;
		System.out.println("Are you sure you want to UNREGISTER? 1.Yes 2.No");
		System.out.println("Input:");
		input = scan.nextInt();
		if(input == 1) {
			try {
				st = conn.createStatement();
				st.executeUpdate("DELETE FROM USER WHERE u_id='" + userId + "';");
				userId = null;
				System.out.println("Unregisterd.");
				MainMenu();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		else if(input == 2) {
			UserCommandMenu();
		}
		else {
			System.out.println("Invalid Input.");
			input = scan.nextInt();
		}
	}
}
