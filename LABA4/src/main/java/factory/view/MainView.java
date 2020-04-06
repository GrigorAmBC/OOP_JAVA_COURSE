package factory.view;

import factory.model.MachineCreationManager;
import factory.model.machine.Machine;
import factory.model.machine.MachineAccessory;
import factory.model.machine.MachineEngine;
import factory.model.machine.MachineFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

  public MainView(@NotNull MachineCreationManager manager) {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(1200, 680));
    setLayout(new GridBagLayout());
    setResizable(false);

    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 0;
    gc.gridy = 0;
    gc.anchor = GridBagConstraints.LINE_START;

    // warehouses
    JPanel warehousePanel = new JPanel();
    warehousePanel.setLayout(new GridLayout(4, 1));
    warehousePanel.setPreferredSize(new Dimension(100, 170));
    WarehousePanel<MachineFrame> framePanel = new WarehousePanel<>(manager.getFrameWarehouse(), "Frames");
    warehousePanel.add(framePanel);

    WarehousePanel<MachineEngine> enginePanel = new WarehousePanel<>(manager.getEngineWarehouse(), "Engines");
    warehousePanel.add(enginePanel);

    WarehousePanel<MachineAccessory> accessoryPanel = new WarehousePanel<>(manager.getAccessoryWarehouse(), "Accessories");
    warehousePanel.add(accessoryPanel);

    WarehousePanel<Machine> machinePanel = new WarehousePanel<>(manager.getMachineWarehouse(), "Machines");
    warehousePanel.add(machinePanel);

    add(warehousePanel, gc);

    // factory
    gc.gridx = 1;
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1, 1));

    FactoryPanel factoryPanel = new FactoryPanel(manager.getFactory());
    panel.add(factoryPanel);//, gc);
    add(panel, gc);

    // suppliers
    gc.gridx = 2;
    JPanel myPanel = new JPanel();
    myPanel.setLayout(new GridLayout(3, 1));
    myPanel.setPreferredSize(new Dimension(200, 170));

    SupplierPanel<MachineFrame> frameSupplierPanel = new SupplierPanel<>(manager.getFrameSuppliers(), "Frame producers");
    myPanel.add(frameSupplierPanel);

    SupplierPanel<MachineEngine> engineSupplierPanel = new SupplierPanel<>(manager.getEngineSuppliers(), "Engine producers");
    myPanel.add(engineSupplierPanel);

    SupplierPanel<MachineAccessory> accessorySupplierPanel = new SupplierPanel<>(manager.getAccessorySuppliers(), "Accessory producers");
    myPanel.add(accessorySupplierPanel);

    add(myPanel, gc);

    // sliders
    gc.gridy = 4;
    gc.gridx = 1;
    JSliderWrapper sliderAccessorySupplier = new JSliderWrapper(manager.getAccessorySuppliers(), "Accessory");
    add(sliderAccessorySupplier, gc);

    gc.gridy = 5;
    gc.gridx = 1;
    JSliderWrapper sliderEngineSupplier = new JSliderWrapper(manager.getEngineSuppliers(), "Engine");
    add(sliderEngineSupplier, gc);

    gc.gridy = 6;
    gc.gridx = 1;
    JSliderWrapper sliderFrameSupplier = new JSliderWrapper(manager.getFrameSuppliers(), "Frame");
    add(sliderFrameSupplier, gc);

    gc.gridy = 7;
    gc.gridx = 1;
    JSliderWrapper sliderDealer = new JSliderWrapper(manager.getDealers(), "Dealer");
    add(sliderDealer, gc);

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
