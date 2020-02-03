package factory.view;

import factory.model.Warehouse;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarehousePanel<P> extends JPanel
        implements ActionListener {
  private Warehouse<P> warehouse;
  private Timer timer = new Timer(50, this);
  private static final Font DEFAULT_FONT = new Font("Tahoma", Font.PLAIN, 20);

  public WarehousePanel(@NotNull Warehouse<P> warehouse, String name) {
    this.warehouse = warehouse;
    setSize(new Dimension(100, 150));
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), name));
    timer.start();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(Color.BLACK);

    g.setFont(DEFAULT_FONT);
    FontMetrics metrics = g.getFontMetrics(DEFAULT_FONT);
    String info = warehouse.getCurrentAmountOfItems() + " / " + warehouse.getCapacity();
    int textWidth = metrics.stringWidth(info);
    g.drawString(info, getWidth() / 2 - textWidth / 2, getHeight() / 2 + metrics.getHeight() / 2);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == timer) {
      repaint();
    }
  }
}
