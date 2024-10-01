package TP6;
import java.awt.Dimension;
		import java.awt.GridLayout;
		import java.awt.event.ActionEvent;
		import java.awt.event.ActionListener;
		import java.io.BufferedReader;
		import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
		import java.io.FileWriter;
		import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BoxLayout;
		import javax.swing.ImageIcon;
		import javax.swing.JButton;
		import javax.swing.JFrame;
		import javax.swing.JLabel;
		import javax.swing.JOptionPane;
		import javax.swing.JPanel;
		import javax.swing.JScrollPane;
		import javax.swing.JTable;
		import javax.swing.JTextField;
		import javax.swing.table.DefaultTableModel;

		public class files extends JFrame implements ActionListener {
		    JLabel lnom = new JLabel("Nom ");
		    JTextField tnom = new JTextField("Saisir le nom ");
		    JLabel lprenom = new JLabel("Prenom ");
		    JTextField tprenom = new JTextField("Saisir le prénom ");
		    JLabel lage = new JLabel("Age");
		    JTextField tage = new JTextField("Saisir l'age");
		    JButton btnSauv = new JButton("Ajouter personne", new ImageIcon("save.png"));
		    JButton btnAfficher = new JButton("Liste personnes");
		    JTable tp;
		    JPanel p, p1, p2, p3;
		    JFrame fr;

		    public files() {
		        super("Fichier binaire");
		        p1 = new JPanel(new GridLayout(0, 2));
		        p1.add(lnom);
		        p1.add(tnom);
		        p1.add(lprenom);
		        p1.add(tprenom);
		        p1.add(lage);
		        p1.add(tage);
		        p2 = new JPanel();
		        p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
		        p2.setMaximumSize(new Dimension(600, 90));
		        p2.setPreferredSize(new Dimension(600, 90));
		        p2.add(p1);

		        p3 = new JPanel();
		        p3.setLayout(new BoxLayout(p3, BoxLayout.LINE_AXIS));
		        p3.setMaximumSize(new Dimension(600, 30));
		        p3.setPreferredSize(new Dimension(600, 30));
		        p3.add(btnSauv);
		        p3.add(btnAfficher);

		        this.setVisible(true);
		        setSize(600, 250);
		        btnSauv.addActionListener(this);
		        btnAfficher.addActionListener(this);
		        p = new JPanel();
		        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		        p.add(p2);
		        p.add(p3);
		        getContentPane().add(p);
		    }

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JButton b = (JButton) e.getSource();
		        if (b.getText().equals("Ajouter personne")) {
		            String nom = tnom.getText();
		            String prenom = tprenom.getText();
		            String age = tage.getText();

		            // Écrire les informations dans le fichier texte
		            try (BufferedWriter writer = new BufferedWriter(new FileWriter("personnes.txt", true))) {
		                writer.write(nom + ";" + prenom + ";" + age);
		                writer.newLine();
		                JOptionPane.showMessageDialog(this, "Personne ajoutée avec succès !");
		            } catch (IOException ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la personne !");
		            }
		        }
		        if (b.getText().equals("Liste personnes")) {
		            Afficher();
		        }
		    }

		    public void Afficher() {
		        String col[] = { "Nom", "Prénom", "Age" };
		        DefaultTableModel model = new DefaultTableModel(col, 0);

		        try (BufferedReader reader = new BufferedReader(new FileReader("personnes.txt"))) {
		            String line;
		            while ((line = reader.readLine()) != null) {
		                String[] parts = line.split(";");
		                if (parts.length == 3) {
		                    model.addRow((Object[]) parts);
		                }
		            }
		        } catch (FileNotFoundException e) {
		            JOptionPane.showMessageDialog(this, "Fichier introuvable !");
		        } catch (IOException e) {
		            JOptionPane.showMessageDialog(this, "Erreur lors de la lecture du fichier !");
		        }

		        if (fr != null) {
		            fr.dispose();
		        }
		        fr = new JFrame("Liste des personnes");
		        tp = new JTable(model);
		        JScrollPane pane = new JScrollPane(tp);
		        fr.add(pane);
		        fr.setSize(new Dimension(600, 300));
		        fr.setVisible(true);
		    }
		        private ArrayList<Personnee> personnes = new ArrayList<>();
		        private JTable jTable;

		          

		        private void ajouterPersonne() {
		            String nom = JOptionPane.showInputDialog("Nom de la personne :");
		            String prenom = JOptionPane.showInputDialog("Prénom de la personne :");
		            int age = Integer.parseInt(JOptionPane.showInputDialog("Âge de la personne :"));

		            Personnee personne = new Personnee(nom, prenom, age);
		            personnes.add(personne);

		          
		            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("personnes.ser"))) {
		                oos.writeObject(personnes);
		                JOptionPane.showMessageDialog(this, "Personne ajoutée et sérialisée avec succès !");
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }

		        private void afficherPersonnes() {
		            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("personnes.ser"))) {
		                personnes = (ArrayList<Personnee>) ois.readObject();
		            } catch (IOException | ClassNotFoundException e) {
		                e.printStackTrace();
		            }

		            DefaultTableModel model = new DefaultTableModel();
		            model.addColumn("Nom");
		            model.addColumn("Prénom");
		            model.addColumn("Âge");

		            for (Personnee personne : personnes) {
		                model.addRow(new Object[]{personne.getNom(), personne.getPrenom(), personne.getAge()});
		            }

		            jTable.setModel(model);
		            
		    }

		    public static void main(String[] args) {
		        new files();
		    }
		}

		    