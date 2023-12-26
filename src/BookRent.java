import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookRent
{
	public Connection con;
	public Statement stmt;  
	public PreparedStatement psmt;  
	public ResultSet rs;
	
	
	
	public BookRent() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
            // 커넥션 URL, 계정 아이디와 패스워드 기술
            String url = "jdbc:oracle:thin:@localhost:1521:xe";  
            String id = "booktest";
            String pwd = "1234"; 
            // 오라클 DB 연결
            con = DriverManager.getConnection(url, id, pwd); 
            // 연결 성공시 콘솔에서 로그 확인
            System.out.println("DB 연결 성공");
        }
        catch (Exception e) {            
            e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		BookRent book = new BookRent();
		while(true) {
			System.out.println("1. 도서 관리 프로그램");
			System.out.println("2. 회원 관리 프로그램");
			System.out.println("3. 반납/대출 프로그램");
			System.out.println("4. 종료");
			System.out.println("메뉴를 선택해주세요.");
			Scanner scc = new Scanner(System.in);
			int choice = scc.nextInt();
			switch(choice) {
			case 1:
				book.listBook();
				break;
			case 2:
				book.listMember();
				break;
			case 3:
				book.rentProgram();
				break;
			case 4:
				System.out.println("종료되었습니다.");
				return;
			}
		}
		
//		
	}
	
	public void showMenu() {
			System.out.println("===도서 관리 프로그램====");
			System.out.println("===메뉴를 선택하세요.====");
			System.out.println("===== 1.책 등록 ======");
			System.out.println("===== 2.책 폐기 ======");
			System.out.println("===== 3.책 조회 ======");
			System.out.println("===== 4.책 전체 조회 ====");
			System.out.println("=======5.종료 =======");
			System.out.println();
			System.out.println("어떤 메뉴를 선택하시겠습니까?");
		
	}
	
	public void listBook() {
		while(true) {
			showMenu();
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			switch(choice) {
			case 1:
				addBook();	
				break;
			case 2:
				deleteBook();
				break;
			case 3:
				findBook();
				break;
			case 4:
				findAllBook();
				break;
			case 5:
				System.out.println("종료되었습니다.");
				return;
			default:
				System.out.println("잘못 입력하셨습니다.");
				break;	
			}
		}
	}
	public void addBook() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("책 제목을 입력하세요.");
			String BOOK_TITLE = sc.nextLine();
			System.out.print("책 수량을 입력해주세요.");
			int BOOK_CNT = sc.nextInt();
			
			String sql = "INSERT INTO BOOK_DB VALUES (seq_book_db.nextval, ?, ?)";
			PreparedStatement psmt1 = con.prepareStatement(sql);
			
		    psmt1.setString(1, BOOK_TITLE);
		    psmt1.setInt(2, BOOK_CNT);
		    
		    int inResult = psmt1.executeUpdate();
		    System.out.println(inResult + "행이 입력되었습니다.");
		    
		    psmt1.close();
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
	}
	
	public void deleteBook() {
		try {
			Scanner scan2 = new Scanner(System.in);
			System.out.println("어떤 책을 삭제하시겠습니까? 도서번호 : ");
			int Book_id_scan = scan2.nextInt();
			String sql = "DELETE FROM BOOK_DB WHERE BOOK_ID=(?)";
			PreparedStatement psmt2 = con.prepareStatement(sql);
			psmt2.setInt(1, Book_id_scan);
			
			int inResult = psmt2.executeUpdate();
			System.out.println(inResult + "행이 삭제되었습니다.");
			psmt2.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void findBook() {
		try{	
			Scanner scan3 = new Scanner(System.in);
			System.out.print("어떤 책을 조회하시겠습니까? 책 제목 : ");
			String Book_id_scan = scan3.nextLine();
			String sql = "select * from book_db where book_title=?";
			PreparedStatement pstm4 = con.prepareStatement(sql);
			pstm4.setString(1, Book_id_scan);
			rs = pstm4.executeQuery();
			while(rs.next()) {
				System.out.println("책번호 : "+rs.getInt("book_id"));
				System.out.println("제  목 : "+rs.getString("book_title"));
				System.out.println("수  량 : " + rs.getInt("book_cnt"));
				System.out.println("----------------------------------------");
			}
			pstm4.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	public void findAllBook() {
		try{	
			String sql = "select * from book_db";
			PreparedStatement pstm4 = con.prepareStatement(sql);
			rs = pstm4.executeQuery();
		
			while(rs.next()) {
				System.out.println("책번호 : "+rs.getInt("book_id"));
				System.out.println("제  목 : "+rs.getString("book_title"));
				System.out.println("수  량 : " + rs.getInt("book_cnt"));
				System.out.println("----------------------------------------");
			}
			pstm4.close();
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void showMemberMenu() {
		System.out.println("===회원 관리 프로그램====");
		System.out.println("===메뉴를 선택하세요.====");
		System.out.println("===== 1.회원 등록 ======");
		System.out.println("===== 2.회원 삭제 ======");
		System.out.println("===== 3.회원 조회 ======");
		System.out.println("===== 4.회원 전체 조회 ====");
		System.out.println("===== 5.블랙리스트 등록/해제 ====");
		System.out.println("=======6.종료 =======");
		System.out.println();
		System.out.println("어떤 메뉴를 선택하시겠습니까?");
	}
	
	public void listMember() {
		while(true) {
			showMemberMenu();
			Scanner scann = new Scanner(System.in);
			int choice = scann.nextInt();
			switch(choice) {
			case 1:
				addMember();	
				break;
			case 2:
				deleteMember();
				break;
			case 3:
				findMember();
				break;
			case 4:
				findAllMember();
				break;
			case 5:
				blackList();
				break;
			case 6:
				System.out.println("종료되었습니다.");
				return;
			default:
				System.out.println("잘못 입력하셨습니다.");
				break;	
			}
		}
	}
	public void addMember() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("회원 아이디를 입력해주세요.");
			String MEM_ID = sc.nextLine();
			System.out.print("회원 이름을 입력해주세요.");
			String MEM_NAME = sc.nextLine();
			System.out.print("블랙리스트 등록 (N만 입력해주세요)");
			String MEM_BLACK = sc.nextLine();
			
			String sql = "INSERT INTO LIBRARY_MEM VALUES (seq_library_mem.nextval, ?, ?, ?)";
			PreparedStatement psmt5 = con.prepareStatement(sql);
			
		    psmt5.setString(1, MEM_ID);
		    psmt5.setString(2, MEM_NAME);
		    psmt5.setString(3, MEM_BLACK);
		    
		    int inResult = psmt5.executeUpdate();
		    System.out.println(inResult + "행이 입력되었습니다.");
		    
		    psmt5.close();
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
	}
	public void deleteMember() {
		try {
			Scanner scan2 = new Scanner(System.in);
			System.out.println("어떤 회원을 삭제하시겠습니까? 회원 아이디: ");
			String Mem_id_scan = scan2.nextLine();
			String sql = "DELETE FROM LIBRARY_MEM WHERE MEM_ID=(?)";
			PreparedStatement psmt2 = con.prepareStatement(sql);
			psmt2.setString(1, Mem_id_scan);
			
			int inResult = psmt2.executeUpdate();
			System.out.println(inResult + "행이 삭제되었습니다.");
			psmt2.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void findMember() {
		try{	
			Scanner scan3 = new Scanner(System.in);
			System.out.print("어떤 회원을 조회하시겠습니까? 회원 아이디 : ");
			String Mem_id_scan = scan3.nextLine();
			String sql = "select * from LIBRARY_MEM where MEM_ID=?";
			PreparedStatement pstm4 = con.prepareStatement(sql);
			pstm4.setString(1, Mem_id_scan);
			rs = pstm4.executeQuery();
			while(rs.next()) {
				System.out.println("회원 번호 : "+rs.getInt("mem_num"));
				System.out.println("회원 아이디 : "+rs.getString("mem_id"));
				System.out.println("회원 이름 : " + rs.getString("mem_name"));
				System.out.println("블랙리스트 여부 : " + rs.getString("mem_black"));
				System.out.println("----------------------------------------");
			}
			pstm4.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	public void findAllMember() {
		try{	
			String sql = "select * from library_mem";
			PreparedStatement pstm4 = con.prepareStatement(sql);
			rs = pstm4.executeQuery();
		
			while(rs.next()) {
				System.out.println("회원 번호 : "+rs.getInt("mem_num"));
				System.out.println("회원 아이디 : "+rs.getString("mem_id"));
				System.out.println("회원 이름 : " + rs.getString("mem_name"));
				System.out.println("블랙리스트 여부 : " + rs.getString("mem_black"));
				System.out.println("----------------------------------------");
			}
			pstm4.close();
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	public void blackList() {
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("블랙리스트 유무 (Y/N)");
			String black = scan.nextLine();
			System.out.println("변경할 회원 아이디를 입력하세요.");
			String mem_id = scan.nextLine();
			
			String sql = "update library_mem set mem_black = ? where mem_id = ?";
			PreparedStatement pstm4 = con.prepareStatement(sql);
			pstm4.setString(1, black);
			pstm4.setString(2, mem_id);
			rs = pstm4.executeQuery();
			int inResult = pstm4.executeUpdate();
			System.out.println(inResult + "행이 수정되었습니다.");
			pstm4.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void rentProgram() {
		
	}
}
