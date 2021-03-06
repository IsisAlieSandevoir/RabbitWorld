package gameInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.swing.JPanel;

import gameActors.AdultRabbit;
import gameActors.BabyRabbit;
import gameActors.PoisonCarrot;
import gameActors.RegularCarrot;
import gameEngine.Controller;

public class Map extends JPanel {
	private static final long serialVersionUID = 1L;
	private Controller ctrl = Controller.getInstance();

	public Map() {
		super();
		this.setFocusable(true);
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keys = e.getKeyCode();
				if(keys == KeyEvent.VK_ENTER || keys == KeyEvent.VK_F5) {
					ctrl.setGameStarted(true);
					repaint();
					removeKeyListener(this);
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;

		try {
			for(int i = 0; i < this.ctrl.getGrid().getLi(); i++) {
				for(int j = 0; j < this.ctrl.getGrid().getCo(); j++) {
					TilesetRW.getInstance().getGrass().drawTile(g2d, j, i);

				}
			}

			for(RegularCarrot rc : this.ctrl.getCarrots()) {
				rc.draw(g2d, rc.getPosCo(), rc.getPosLi());
			}

			for(PoisonCarrot pc : this.ctrl.getPoisons()) {
				pc.draw(g2d, pc.getPosCo(), pc.getPosLi());
			}

			for(AdultRabbit ar : this.ctrl.getAdultRabbits()) {
				ar.draw(g2d, ar.getPosCo(), ar.getPosLi());
			}

			for(BabyRabbit br : this.ctrl.getBabyRabbits()) {
				br.draw(g2d, br.getPosCo(), br.getPosLi());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(!ctrl.isGameStarted()) {
			g2d.setColor(Color.white);
			Font f = new Font("Courier", Font.BOLD, 50);
			g2d.setFont(f);
			g2d.drawString("PRESS ENTER TO START", 20, 300);
			g2d.drawRect(10, 250, 620, 60);
			GradientPaint pausedGrad = new GradientPaint(0, 0, new Color(0, 0, 224, 50), 
					this.getWidth(), this.getHeight(), new Color(0, 0, 224, 50));
			g2d.setPaint(pausedGrad);
			g2d.fill(new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight()));
		}

		if(ctrl.gameOver()) {
			Rectangle2D rect = new Rectangle2D.Double(0, 250,640,90);
			g2d.setColor(new Color(255,255,255,150));
			g2d.fill(rect);
			g2d.draw(rect);
			g2d.setColor(Color.black);
			Font f = new Font("Courier", Font.BOLD, 50);
			g2d.setFont(f);
			g2d.drawString("GAME OVER", 160, 300);
			g2d.setColor(Color.blue);
			Font f2 = new Font("Courier", Font.BOLD, 20);
			g2d.setFont(f2);
			g2d.drawString("To play again, exit the window and enter new inputs !", 6, 330);
			ctrl.setGameInited(false);
			ctrl.setGameInited(false);
		}
	}
}
