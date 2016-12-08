/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTool;

import io.milton.event.Event;
import io.milton.event.EventListener;
import io.milton.event.EventManager;
import io.milton.event.EventManagerImpl;
import io.milton.sync.SyncCommand;
import io.milton.sync.SyncJob;
import io.milton.sync.event.DownloadSyncEvent;
import io.milton.sync.event.TransferProgressEvent;
import io.milton.sync.event.UploadSyncEvent;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//guiTool//SyncTool//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "SyncToolTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "guiTool.SyncToolTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 /Window */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_SyncToolAction",
        preferredID = "SyncToolTopComponent"
)
@Messages({
    "CTL_SyncToolAction=SyncTool",
    "CTL_SyncToolTopComponent=SyncTool Window",
    "HINT_SyncToolTopComponent=This is a SyncTool window"
})
public final class SyncToolTopComponent extends TopComponent {

    private static final Logger LOG = Logger.getLogger("org.netbeans.modules.ksync");

    DefaultListModel model;
    final static String JOB = "job";
    final static String sDbFile = "~/syncdb";
    static File file;
    static Properties jobFile;
    int sizeJobs;
    EventManager eventManager;

    public SyncToolTopComponent() {
        initComponents();
        setName(Bundle.CTL_SyncToolTopComponent());
        setToolTipText(Bundle.HINT_SyncToolTopComponent());

        File fUserHome = new File(System.getProperty("user.home"));
        file = new File(fUserHome, ".ksync.properties");
        jobFile = new Properties();

        if (!file.exists()) {
            createBlankFile(file);
        } else {
            loadProperties(jobFile);;
        }
        eventManager = new EventManagerImpl();
        registerEvents();

        model = new DefaultListModel();
        list_Jobs.setModel(model);
        list_Jobs.setCellRenderer(new JObCellRenderer());
        updateJobList(readObject());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_Jobs = new javax.swing.JList<>();
        jPanel6 = new javax.swing.JPanel();
        btn_addJob = new javax.swing.JButton();
        btn_editJob = new javax.swing.JButton();
        btn_deleteJob = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setAttentionHighlight(false);
        setAutoscrolls(true);
        setFocusable(true);
        setName("Form"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, "Sync Jobs ");
        jLabel1.setName("jLabel1"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jSeparator1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        list_Jobs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        list_Jobs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        list_Jobs.setAutoscrolls(false);
        list_Jobs.setName("list_Jobs"); // NOI18N
        jScrollPane1.setViewportView(list_Jobs);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.white, java.awt.Color.white));
        jPanel6.setName("jPanel6"); // NOI18N

        btn_addJob.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btn_addJob, "Add");
        btn_addJob.setName("btn_addJob"); // NOI18N
        btn_addJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addJobActionPerformed(evt);
            }
        });

        btn_editJob.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btn_editJob, "Edit");
        btn_editJob.setName("btn_editJob"); // NOI18N
        btn_editJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editJobActionPerformed(evt);
            }
        });

        btn_deleteJob.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btn_deleteJob, "Delete");
        btn_deleteJob.setBorder(null);
        btn_deleteJob.setInheritsPopupMenu(true);
        btn_deleteJob.setName("btn_deleteJob"); // NOI18N
        btn_deleteJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteJobActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-dark.png"))); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_addJob, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(btn_editJob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_deleteJob, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btn_addJob, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btn_editJob, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btn_deleteJob, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addJobActionPerformed
        // TODO add your handling code here:
        //        model.add(0, "job \n " + "des");
        display();
    }//GEN-LAST:event_btn_addJobActionPerformed

    private void btn_editJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editJobActionPerformed
        // TODO add your handling code here:
        int index = list_Jobs.getSelectedIndex();
        if (index != -1) {
            displaUpdate(index);
        }
    }//GEN-LAST:event_btn_editJobActionPerformed

    private void btn_deleteJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteJobActionPerformed
        // TODO add your handling code here:
        System.out.println(" list_Jobs.getSelectedIndex()" + list_Jobs.getSelectedIndex());
        int yes = JOptionPane.showConfirmDialog(null, "Do you want Delete Job ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (yes == 0) {
            int index = list_Jobs.getSelectedIndex();
            int i = 0;

            if (model.getSize() <= 1) {
                rProperty(i, "localPath");
                rProperty(i, "remoteAddress");
                rProperty(i, "repositry");
                rProperty(i, "branch");
                rProperty(i, "user");
                rProperty(i, "password");
                rProperty(i, "LocalReadonly");
            } else {

                for (i = index; i < model.getSize() - 1; i++) {
                    rwProperty(i, "localPath");
                    rwProperty(i, "remoteAddress");
                    rwProperty(i, "repositry");
                    rwProperty(i, "branch");
                    rwProperty(i, "user");
                    rwProperty(i, "password");
                    rwProperty(i, "LocalReadonly");

                }
                rProperty(i, "localPath");
                rProperty(i, "remoteAddress");
                rProperty(i, "repositry");
                rProperty(i, "branch");
                rProperty(i, "user");
                rProperty(i, "password");
                rProperty(i, "LocalReadonly");
            }
           
            sizeJobs = Integer.parseInt(jobFile.getProperty("sizeJobs", "0"));
            wProperty(-1, "sizeJobs", String.valueOf(Integer.parseInt(jobFile.getProperty("sizeJobs", "0")) - 1));
            saveProperties(jobFile);
            loadProperties(jobFile);
            model.remove(index);
        }
    }//GEN-LAST:event_btn_deleteJobActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addJob;
    private javax.swing.JButton btn_deleteJob;
    private javax.swing.JButton btn_editJob;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList<String> list_Jobs;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
        //   updateJobList(readObject());
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    private void display() {

        addJob panel = new addJob(Integer.parseInt(jobFile.getProperty("sizeJobs", "0")));

        int result = JOptionPane.showConfirmDialog(list_Jobs, panel, "Add Job Sync",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("addjob");
            SyncJob job = panel.doAddJob();

            if (job != null) {

                model.addElement(new JobModel(job.getLocalDir().getAbsolutePath(), job.getRemoteAddress()));
                //add new sync job
                try {
                    SyncCommand.start(new File(sDbFile), Arrays.asList(job), eventManager).get(0);
                } catch (Exception ex) {

                    LOG.log(Level.WARNING, ex.getMessage());
                }

                saveProperties(jobFile);
                loadProperties(jobFile);
            }

        } else {
            System.out.println("Cancelled");
        }

    }

    private void displaUpdate(int index) {

        updateJob panel = new updateJob(index);

        int result = JOptionPane.showConfirmDialog(list_Jobs, panel, "Update Job Sync",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

            SyncJob job = panel.doUpdateJob();

            if (job != null) {

                model.set(index, new JobModel(job.getLocalDir().getAbsoluteFile().toString(), job.getRemoteAddress()));
                /// run updated job
                try {
                    SyncCommand.start(new File(sDbFile), Arrays.asList(job), eventManager).get(0);
                } catch (Exception ex) {
                    LOG.log(Level.WARNING, ex.getMessage());

                }

                saveProperties(jobFile);
                loadProperties(jobFile);

            }

        } else {
            System.out.println("Cancelled");
        }

    }

    private void registerEvents() {
        eventManager.registerEventListener(new EventListener() {
            @Override
            public void onEvent(Event event) {

                UploadSyncEvent e = (UploadSyncEvent) event;
                File f = e.getLocalFile();
                LOG.log(Level.INFO, "Upload {0}", f.getAbsolutePath());
            }
        }, UploadSyncEvent.class);

        eventManager.registerEventListener(new EventListener() {
            @Override
            public void onEvent(Event event) {

                DownloadSyncEvent e = (DownloadSyncEvent) event;
                File f = e.getLocalFile();
                if (f != null) {
                    LOG.log(Level.INFO, "Download {0}", f.getAbsolutePath());
                }
            }
        }, DownloadSyncEvent.class);

        eventManager.registerEventListener(new EventListener() {
            @Override
            public void onEvent(Event event) {

                TransferProgressEvent e = (TransferProgressEvent) event;

                LOG.log(Level.INFO, "TransferProgress {0}    {1}", new Object[]{e.getFileName(), e.getPercent()});
            }
        }, TransferProgressEvent.class);
    }

    public class JobModel {

        private final String local, remote;
        URL imagePath;
        private ImageIcon image;

        public JobModel(String local, URL imagePath, String remote) {
            this.local = local;
            this.imagePath = imagePath;
            this.remote = remote;
        }

        public JobModel(String local, String remote) {
            this.local = local;
            this.remote = remote;

            imagePath = getClass().getResource("/images/upload.png");
        }

        public String getLocal() {
            return local;
        }

        public URL getImagePath() {
            return imagePath;
        }

        public String getRemote() {
            return remote;
        }

        public ImageIcon getImage() {
            if (image == null) {
                image = new ImageIcon(imagePath);
            }
            return image;
        }

        // Override standard toString method to give a useful result
    }

    class JObCellRenderer extends JLabel implements ListCellRenderer {

        private final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
        Object value;

        public JObCellRenderer() {
            setOpaque(true);
            setIconTextGap(20);
            setFont(new java.awt.Font("Arial", 0, 13));

        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {

            JobModel entry = (JobModel) value;
            setSize(list.getWidth(), 12);
            setToolTipText("Remote host: " + entry.getRemote());
            setText(entry.getLocal());
            setIcon(entry.getImage());

            if (isSelected) {
                setBackground(HIGHLIGHT_COLOR);
                setForeground(Color.white);
            } else {
                setBackground(Color.white);
                setForeground(Color.black);
            }
            return this;
        }
    }

    public ArrayList<SyncJob> readObject() {

        String localPathProperty = null, repositryProperty = null, branchProperty = null, userProperty = null, passwordProperty = null, LocalReadonlyProperty = null, remoteAddressProperty = null;
        ArrayList<SyncJob> jobs = new ArrayList();

        for (int num = 0; num < sizeJobs; num++) {
            SyncJob job = new SyncJob();
            localPathProperty = jobFile.getProperty(JOB + "." + num + "." + "localPath");
            if (localPathProperty == null) {
                break;
            }

            repositryProperty = jobFile.getProperty(JOB + "." + num + "." + "repositry");
            branchProperty = jobFile.getProperty(JOB + "." + num + "." + "branch");
            userProperty = jobFile.getProperty(JOB + "." + num + "." + "user");
            passwordProperty = jobFile.getProperty(JOB + "." + num + "." + "password");
            LocalReadonlyProperty = jobFile.getProperty(JOB + "." + num + "." + "LocalReadonly");
            remoteAddressProperty = jobFile.getProperty(JOB + "." + num + "." + "remoteAddress");
            job.setLocalDir(new File(localPathProperty));
            job.setLocalReadonly(Boolean.valueOf(LocalReadonlyProperty));
            job.setPwd(passwordProperty);
            job.setUser(userProperty);
            job.setMonitor(true);

            String q_host = remoteAddressProperty + "repositories/" + repositryProperty + "/" + branchProperty + "/";
            job.setRemoteAddress(q_host);
            jobs.add(job);
        }

        return jobs;
    }
    File localFile;

    void updateJobList(ArrayList<SyncJob> jobs) {
        if (!jobs.isEmpty() && jobs != null) {

            try {
                SyncCommand.start(new File(sDbFile), jobs, eventManager);
            } catch (Exception ex) {
                System.out.println(" ex " + ex.getMessage());
            }

            for (SyncJob job : jobs) {
                localFile = job.getLocalDir();

                model.addElement(new JobModel(job.getLocalDir().getAbsoluteFile().toString(), job.getRemoteAddress()));

            }
        }

    }

    void loadProperties(Properties p) {
        FileInputStream fi;
        try {
            fi = new FileInputStream(file);
            p.load(fi);
            fi.close();
            sizeJobs = Integer.parseInt(jobFile.getProperty("sizeJobs", "0"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(updateJob.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(updateJob.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("After Loading properties:" + p);
    }

    private static void createBlankFile(File f) {
        try {
            FileOutputStream fout = new FileOutputStream(f);

            byte[] b = "".getBytes();
            fout.write(b);
            fout.close();
        } catch (Throwable e) {
            System.out.println("Couldnt create empty properties file, not big deal");
        } finally {
        }
    }

    static void wProperty(int index, String Property, String value) {
        if ("sizeJobs".equals(Property)) {
            jobFile.setProperty(Property, value);
        } else {
            jobFile.setProperty(JOB + "." + index + "." + Property, value);
        }

    }

    static void rwProperty(int index, String Property) {
        jobFile.setProperty(JOB + "." + index + "." + Property, jobFile.getProperty(JOB + "." + (index + 1) + "." + Property));

    }

    void rProperty(int index, String Property) {

        jobFile.remove(JOB + "." + index + "." + Property);

    }

    void saveProperties(Properties p) {
        FileOutputStream fr;
        try {
            fr = new FileOutputStream(file);
            p.store(fr, "Properties");
            fr.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(addJob.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(addJob.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("After saving properties:" + p);
    }
}