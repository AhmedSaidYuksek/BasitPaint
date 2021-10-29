import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class Main extends JFrame implements MouseInputListener{
	int x1=0 ,x2=0,y1=0,y2=0,x2_control=0,y2_control=0;
	Dikdortgen tasinan=null;
	int sekil_sayisi=0;	
	ArrayList<Cizim> cizim=new ArrayList<>();
	ArrayList<Dikdortgen> diktortgenler=new ArrayList<>();	
	int renk=0;
	int sekil=0;
	int remove=0;
	boolean nokta_kontrol=false;
	boolean delete=false;
	public Main(){	
		addMouseListener(this);
		addMouseMotionListener(this);
		setSize(500, 500);
		setLayout(new BorderLayout());
		JButton dikdortgen=new JButton("dikdortgen");
		dikdortgen.addActionListener(new ActionListener() {				
			public void actionPerformed(ActionEvent e) {
				sekil=1;
				//repaint();
			}
		});
		JButton cizgi=new JButton("ciz");
		cizgi.addActionListener(new ActionListener() {				
			public void actionPerformed(ActionEvent e) {
				sekil=0;
				//repaint();
			}
		});
		JButton tasi=new JButton("tasi"); 
		tasi.addActionListener(new ActionListener() {				
			public void actionPerformed(ActionEvent e) {
				sekil=2;
				//repaint();
			}
		});
		JPanel siyah=new JPanel(); 
		siyah.setBackground(Color.black);		
		
		JPanel yesil=new JPanel(); 
		yesil.setBackground(Color.green);
		
		JPanel mavi=new JPanel(); 
		mavi.setBackground(Color.blue);	
		
		JPanel tusPaneli=new JPanel();
		tusPaneli.setLayout(new FlowLayout());
		tusPaneli.setBackground(Color.gray);
		tusPaneli.add(cizgi);
		tusPaneli.add(dikdortgen);
		tusPaneli.add(tasi);
		tusPaneli.add(siyah);	
		tusPaneli.add(yesil);		
		tusPaneli.add(mavi);
		
		add(tusPaneli,BorderLayout.NORTH);
		setVisible(true);
	}
	public int find(int konumX,int konumY) {
		for(int i=0;i<diktortgenler.size();i++) {		
			if(diktortgenler.get(i).x1<=konumX&&(diktortgenler.get(i).x2+diktortgenler.get(i).x1)>=konumX&&diktortgenler.get(i).y1<=konumY&&(diktortgenler.get(i).y2+diktortgenler.get(i).y1)>=konumY) {
				 return i;
			}
		}return -1;
	}	
	public void paint (Graphics g) {		
		super.paint(g);	
		
		if(renk==0) {
			g.setColor(Color.black);
		}
		else if(renk==1) {
			g.setColor(Color.green);
		}
		else {
			g.setColor(Color.blue);	
		}int dikdortgen_sayac=0,cizim_sayac=0;
		for(int i=0;i<sekil_sayisi;i++) {
			if(dikdortgen_sayac<diktortgenler.size()&&diktortgenler.get(dikdortgen_sayac).sira==i) {
				if(diktortgenler.get(dikdortgen_sayac).renk==0) {
					g.setColor(Color.black);
				}
				else if(diktortgenler.get(dikdortgen_sayac).renk==1) {
					g.setColor(Color.green);
				}
				else {
					g.setColor(Color.blue);	
				}g.fillRect(diktortgenler.get(dikdortgen_sayac).x1,diktortgenler.get(dikdortgen_sayac).y1, diktortgenler.get(dikdortgen_sayac).x2, diktortgenler.get(dikdortgen_sayac).y2);
				dikdortgen_sayac++;
			}else if(cizim.size()!=0){
				if(cizim.get(cizim_sayac).renk==0) {
					g.setColor(Color.black);
				}
				else if(cizim.get(cizim_sayac).renk==1) {
					g.setColor(Color.green);
				}
				else {
					g.setColor(Color.blue);	
				}for(int j=0;j<cizim.get(cizim_sayac).ciz.size();j++) {
					g.fillOval(cizim.get(cizim_sayac).ciz.get(j).x1, cizim.get(cizim_sayac).ciz.get(j).y1, cizim.get(cizim_sayac).ciz.get(j).x2, cizim.get(cizim_sayac).ciz.get(j).y2);
				}
				cizim_sayac++;			
			}			
		}	
	}	
	public void mouseClicked(MouseEvent e) {		
		if(e.getY()>=42&&e.getY()<=50) {
			if(e.getX()>=338&&e.getX()<=346) {				
				renk=0;
			}else if(e.getX()>=352&&e.getX()<=360) {				
				renk=1;
			}
			else if(e.getX()>=366&&e.getX()<=374) {
				renk=2;
			}
		}
	}
	public void mousePressed(MouseEvent e) {			
		y1=e.getY();
		x1=e.getX();
		if(sekil==0) {
			cizim.add(new Cizim(sekil_sayisi,renk));
		}
		else if(sekil==2) {
			int as=find(e.getX(), e.getY());
			if(as!=-1) {
				tasinan=new Dikdortgen(diktortgenler.get(as).x1, diktortgenler.get(as).y2, diktortgenler.get(as).x2, diktortgenler.get(as).y2, diktortgenler.get(as).renk, sekil_sayisi);
				diktortgenler.get(as).y2=0;diktortgenler.get(as).x2=0;			
				delete=true;				
			}			
		}		
	}
	public void mouseReleased(MouseEvent e) {			
		y2=e.getY();
		x2=e.getX();		
		if(sekil==0) {			
			if(cizim.get(cizim.size()-1).ciz.size()==0) {
				cizim.get(cizim.size()-1).ciz.add(new Ciz(e.getX(), e.getY(), 4, 4));
			}sekil_sayisi++;
		}	
		else if(sekil==1) {
			diktortgenler.add(new Dikdortgen(x1, y1, x2-x1, y2-y1, renk,sekil_sayisi));	
			sekil_sayisi++;
		}else if(sekil==2&&delete) {
			diktortgenler.add(new Dikdortgen(e.getX(), e.getY(), tasinan.x2, tasinan.y2, tasinan.renk,sekil_sayisi));	sekil_sayisi++;
			delete=false;
		}repaint();	
		
	}
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
	public void mouseDragged(MouseEvent e) {	
		Graphics g=getGraphics();
		if(renk==0)
			g.setColor(Color.BLACK);
		else if(renk==1)
            g.setColor(Color.green);
		else if(renk==2)
			g.setColor(Color.blue);
		if(sekil==0) {	 
			cizim.get(cizim.size()-1).ciz.add(new Ciz(e.getX(),e.getY(),4,4));
            g.fillOval(e.getX(),e.getY(),4,4);
		}else if(sekil==1) {
			x2=e.getX();
			y2=e.getY();			
			g.fillRect(x1, y1, x2-x1, y2-y1);
			
		}else if(sekil==2&&delete){
			x2=e.getX();
			y2=e.getY();
			if(tasinan.renk==0)
				g.setColor(Color.BLACK);
			else if(tasinan.renk==1)
	            g.setColor(Color.green);
			else if(tasinan.renk==2)
				g.setColor(Color.blue);
			g.fillRect(x2, y2, tasinan.x2, tasinan.y2);
			repaint();
		}	
	}
	public void mouseMoved(MouseEvent e) {
		
	}	

	public static void main(String[] args) {
		Main a=new Main();

	}
	
}
