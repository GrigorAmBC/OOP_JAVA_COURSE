package factory.view;

import factory.model.interfaces.PeriodSetter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JSliderWrapper extends JPanel implements ChangeListener {
  private List<PeriodSetter> subjects = new ArrayList<>();

  public JSliderWrapper(@NotNull List<? extends PeriodSetter> subjects, String name) {
    this.subjects.addAll(subjects);
    setupSlider(name);
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    for (PeriodSetter setter : subjects) {
      setter.setPeriod(((JSlider) e.getSource()).getValue() *10);
    }
  }

  private void setupSlider(String name) {
    JSlider slider = new JSlider(0, 10);
    slider.addChangeListener(this);
    slider.setPaintLabels(true);
    slider.setMajorTickSpacing(1);
    slider.setSnapToTicks(true);
    add(slider);

    JTextArea textArea = new JTextArea(name);
    textArea.setFont(new Font("Tahoma", Font.PLAIN, 22));
    textArea.setEnabled(false);
    textArea.setDisabledTextColor(Color.BLACK);

    add(textArea);
  }
}
