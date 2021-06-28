package lifeGame;

public class GameMap {
	private final int sur=0;
	private final int death=1;
	private final int keep=2;
	int row,col;
	boolean[][] map;
	public  GameMap(int nRow,int nCol)
	{
		row = nRow;
		col = nCol;
		map = new boolean[row][col];
	}
	//随机生成地图数组
	public void randomMap()
	{
		GameLogic logic = new GameLogic();
		logic.initMap(map);
	}
	//更改数组大小
	public void changeMap(int nRow,int nCol){
		row = nRow;
		col = nCol;
		map = new boolean[row][col];
	}

	//清空数组
	public void clearMap(){
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++) {
				map[i][j] = false;
			}
		}
	}
	//得到本状态下地图数组״̬
	public boolean[][] getMap() {
		return map;
	}
	//下一状态״̬
	public void NextStatus() {
		boolean [][]temp_map=new boolean[row][col];
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[i].length;j++) {
				GameLogic logic = new GameLogic();
				int status=logic.nextStatus(map,i,j);
				if(status==sur)
					temp_map[i][j]=true;
				if(status==death)
					temp_map[i][j]=false;
			    if(status==keep)
			    	temp_map[i][j]=map[i][j];			    	
			}
		}
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[i].length;j++) {
				map[i][j]=temp_map[i][j];
			}
		}
	}
}
