package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import information.FrameLocation;

public class InviteFrame extends JFrame {

   JPanel mainPanel;
   JPanel centerPanel;
   JPanel bottomPanel;
   JButton closeBtn;
   JScrollPane scroll;
   Image background;
   InviteRowsPanel inviteRowsPanel;

   public InviteFrame() {
      setTitle("초대하기");
      setSize(350, 500);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocation(FrameLocation.X, FrameLocation.Y);

      newComponents();
      setComponents();
      addComponents();

      setVisible(true);
   }

   public void newComponents() {
      mainPanel = new BackGroundPanel();
      centerPanel = new JPanel();
      bottomPanel = new JPanel();
      closeBtn = new JButton(new ImageIcon("btnImg/close.png")); // * 닫기 * //
      scroll = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      inviteRowsPanel = new InviteRowsPanel();

   }

   public void setComponents() {
      mainPanel.setOpaque(false);
      mainPanel.setBackground(new Color(0, 0, 0, 0));
      mainPanel.setLayout(new BorderLayout());

      centerPanel.setOpaque(false);
      centerPanel.setBackground(new Color(0, 0, 0, 0));

      scroll.setOpaque(false);
      scroll.setBackground(new Color(0, 0, 0, 0));
      scroll.getViewport().setOpaque(false);
      scroll.setBorder(BorderFactory.createEmptyBorder()); // * 스크롤팬 테두리 제거 * //

      closeBtn.setPressedIcon(new ImageIcon("btnImg/closePush.png"));
      closeBtn.setFocusPainted(false);
      closeBtn.setContentAreaFilled(false);
      closeBtn.setBorderPainted(false);
      closeBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });

      inviteRowsPanel.setOpaque(false);
      inviteRowsPanel.setBackground(new Color(0, 0, 0, 0));

   }

   public void addComponents() {
      add(mainPanel);
      mainPanel.add(scroll, BorderLayout.CENTER);
      mainPanel.add(bottomPanel, BorderLayout.SOUTH);
      bottomPanel.add(closeBtn);
      centerPanel.add(inviteRowsPanel);
   }

   class InviteRowsPanel extends JPanel {

      public InviteRowsPanel() {
         setLayout(new GridLayout(0, 1, 0, 0));
         InviteUserPanel userPanel = new InviteUserPanel("minwook");
         InviteUserPanel userPanel2 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel3 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel4 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel5 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel6 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel7 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel8 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel9 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel10 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel11 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel12 = new InviteUserPanel("minwook");
         InviteUserPanel userPanel13 = new InviteUserPanel("minwook");

         addUserList(userPanel);
         addUserList(userPanel2);
         addUserList(userPanel3);
         addUserList(userPanel4);
         addUserList(userPanel5);
         addUserList(userPanel6);
         addUserList(userPanel7);
         addUserList(userPanel8);
         addUserList(userPanel9);
         addUserList(userPanel10);
         addUserList(userPanel11);
         addUserList(userPanel12);
         addUserList(userPanel13);

         setVisible(true);

      }

      public void addUserList(InviteUserPanel inviteUserPanel) {
         this.add(inviteUserPanel);
         this.revalidate();
         this.repaint();
      }
   }

   class BackGroundPanel extends JPanel {
      public BackGroundPanel() {
      }

      protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.drawImage(background, 0, 0, this);
      }
   }

   public static void main(String[] args) {
      new InviteFrame();
   }
}