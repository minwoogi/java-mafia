package information;

import java.awt.Dimension;
import java.awt.Toolkit;

public class FrameLocation {
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int X = (screenSize.width - 1100) / 2;
	public static int Y = (screenSize.height - 600) / 2;
	
}
