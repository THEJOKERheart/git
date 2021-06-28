package lifeGame;
import java.util.Random;

public class GameLogic {
	private final int x = 0;
	private final int y = 1;
	private final int sur=0;
	private final int death=1;
	private final int keep=2;
	private static int DeathRate = 50; //默认初始死亡率为50%
	int[][] dir = new int[8][2];
	boolean SurvivalStatus;
	
	public GameLogic() {
		SurvivalStatus = false;
		initDirection();
	}
	//初始化数组
	public void initMap(boolean[][] map)
	{
		for(int i = 0;i<map.length;i++)
		{
			for(int j = 0; j <map[i].length;j++)
			{
				Random random = new Random();
				SurvivalStatus = random.nextInt(100) < DeathRate? false:true;
				map[i][j] = SurvivalStatus;
			}
		}
	}
	//得到每个细胞的周围细胞数量
	public int getSurvivalNum(boolean[][] map,int row,int col)
	{
		int num = 0;
		for(int i = 0;i<8;i++)
		{
			row += dir[i][y];
			col += dir[i][x];
			if(row == -1 || col == -1 || row == map.length || col ==  map[0].length)
				continue;
			if(map[row][col] == true)
				num++;	
		}
		return num;
	}
	//一亡，四死，三活，二不变
	public int nextStatus(boolean[][] map,int row,int col)
	{
		int num = getSurvivalNum(map,row,col);
		if(num <= 1) 
			return death;
		if(num == 3)
			return sur;
		if(num >= 4)
			return death;
		return keep;
	}
	public static void setDeathRate(int deathRate) {
		DeathRate = deathRate;
	}
	private void initDirection()
	{
		//定义路径方向
		dir[0][x] = 1;
		dir[0][y] = 0;
		 
		dir[1][x] = 0;
		dir[1][y] = 1;
	 
		dir[2][x] = -1;
		dir[2][y] = 0;
		 
		dir[3][x] = -1;
		dir[3][y] = 0;
	 
		dir[4][x] = 0;
		dir[4][y] = -1;
		 
		dir[5][x] = 0;
		dir[5][y] = -1;
		 
		dir[6][x] = 1;
		dir[6][y] = 0;
		 
		dir[7][x] = 1;
		dir[7][y] = 0;
	}
}
