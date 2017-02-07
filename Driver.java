import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Driver extends JPanel implements MouseListener, KeyListener {
	public static void main(String[] args) {
		Driver driver = new Driver();
		driver.run();
	}

	public void run() {
		_frame = new JFrame("Tweetable Art");
		_frame.setSize(_width + 17, _height + 36);
		_frame.setLocationRelativeTo(null);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(_width, _height);
		
		_design = new Julia(
			_quality,
			Design.ORIGINX,
			Design.ORIGINY,
			_pallete,
			_iterations
		);
		_image = _design.image();

		_frame.add(this);
		
		_frame.setVisible(true);
		this.addMouseListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

	public void paintComponent(Graphics g) {
		g.drawImage(_image, 0, 0, _width, _height, null);
	}

	public void mouseClicked(MouseEvent e) {
		double x = e.getX(), y = e.getY();
		double r = x / _width * _design.xWidth() + _design.rangeX()[0];
		double i = y / _height * _design.yHeight() + _design.rangeY()[0];
		double c = e.getButton() == MouseEvent.BUTTON1 ? 0.1 : 10;
		_design.rangeX(new double[] {
			r - c * _design.xWidth(),
			r + c * _design.xWidth(),
		});
		_design.rangeY(new double[] {
			i - c * _design.yHeight(),
			i + c * _design.yHeight(),
		});
		_image = _design.image();
		_frame.repaint();
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
    public void keyPressed(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
            _design = new Julia(
                    _quality,
                    Design.ORIGINX,
                    Design.ORIGINY,
                    _pallete,
                    _iterations
            );
            _image = _design.image();
            this.repaint();
        } else if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
           	_pallete = Pallete.STOCK_PALLETES[
           		(int) (Math.random() * Pallete.STOCK_PALLETES.length)
           	];
        	_design = new Julia(
                    _quality,
                    Design.ORIGINX,
                    Design.ORIGINY,
                    _pallete,
                    _iterations
            );
            _image = _design.image();
            this.repaint();
        }
    }
    public void keyReleased(KeyEvent e) {}

	private JFrame _frame;
	private Design _design;
	private BufferedImage _image;
	private int _width = 512;
	private int _height = 512;
	private int _quality = 512;
	private int _iterations = 100;
	private Pallete _pallete = Pallete.CORAL;
}
