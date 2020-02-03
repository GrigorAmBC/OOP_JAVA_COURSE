package factory.view;

import factory.model.Supplier;
import factory.model.machine.MachinePart;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SupplierPanel<P extends MachinePart> extends JPanel implements ActionListener {

  private List<Supplier<P>> suppliers;
  private Timer timer = new Timer(50, this);
  private static final Font DEFAULT_FONT = new Font("Tahoma", Font.PLAIN, 20);


  public SupplierPanel(@NotNull List<Supplier<P>> supplier, String name) {
    this.suppliers = supplier;
    setSize(new Dimension(100, 150));
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), name));
    timer.start();
  }

  private int getCount() {
    int result = 0;
    for (Supplier<P> supplier : suppliers) {
      result += supplier.getNumberOfItemsProduced();
    }
    return result;
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(Color.BLACK);

    g.setFont(DEFAULT_FONT);
    FontMetrics metrics = g.getFontMetrics(DEFAULT_FONT);
    String info = "Compound: " + getCount();

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
