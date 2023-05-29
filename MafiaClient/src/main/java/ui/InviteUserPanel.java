package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import handling.netty.ClientHandler;
import handlinig.packet.WaitingRoomPacket;

public class InviteUserPanel extends JPanel {

   private JPanel nickNamePanel;
   private JPanel invitePanel;
   private JButton nickNameTf; 
   private JButton inviteBtn;
   private Font tfFont;
   private String nickName;

   public InviteUserPanel(String nickName) {
      this.nickName = nickName;
      setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
      this.setPreferredSize(new Dimension(300, 50));
      newComponents();
      setComponents();
      addComponents();

      setVisible(true);
   }

   public void newComponents() {
      nickNamePanel = new JPanel();
      invitePanel = new JPanel();

      nickNameTf = new JButton(new ImageIcon("btnImg/inviteNick.png"));
      inviteBtn = new JButton(new ImageIcon("btnImg/invite.png"));

      tfFont = new Font("", Font.BOLD, 15);
   }

   public void setComponents() {
      nickNamePanel.setLayout(new BorderLayout());
      invitePanel.setLayout(new BorderLayout());

      nickNameTf.setFont(tfFont);
      inviteBtn.setFont(tfFont);

      nickNameTf.setPreferredSize(new Dimension(210, 50));
      inviteBtn.setPreferredSize(new Dimension(90, 50));
      inviteBtn.setPressedIcon(new ImageIcon("btnImg/invitePush.png"));
      
      nickNameTf.setHorizontalTextPosition(JButton.CENTER);
      nickNameTf.setText(nickName);

      btnInvisible(nickNameTf);
      btnInvisible(inviteBtn);
      
      inviteBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ClientHandler.send(WaitingRoomPacket.makeInvitePacket(nickName));
         }
      });

   }

   public void addComponents() {
      nickNamePanel.add(nickNameTf, BorderLayout.CENTER);
      invitePanel.add(inviteBtn, BorderLayout.CENTER);
      this.add(nickNamePanel);
      this.add(invitePanel);

   }

   public void btnInvisible(JButton btn) { // * 버튼 투명화(이미지 보이게) * //
      btn.setFocusPainted(false);
      btn.setContentAreaFilled(false);
      btn.setBorderPainted(false);
   }

}