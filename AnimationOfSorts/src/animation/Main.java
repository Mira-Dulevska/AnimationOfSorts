package animation;

import java.util.*;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.lang.Thread;

public class Main extends JFrame {

    static final int MAXN=501;
    Random rand = new Random();

    static JPanel panel=new JPanel();

    static int n,speed;
    static final int widthnum=1000,heightnum=500;

    Element[] arr=new Element[MAXN];
    Element[] temp = new Element[MAXN];

    public void setval(int id,int val){
        arr[id].value=val;

        double position = 1.0*widthnum/(2*n+1)*(1+2*(id-1));
        int roundedpos=(int) position;

        arr[id].setBounds( roundedpos , 500-(heightnum*val/n) , widthnum/(2*n+1) , (heightnum*val/n) );
    }

    public void swapel(int x,int y){
        int s1=arr[x].value,s2=arr[y].value;
        setval(x,s2); setval(y,s1);
    }

    public void delay() {
        try {
            Thread.sleep(speed);
        } catch (Exception e) {}
    }

    public void bubbleSort(){
        for(int i=1;i<=n-1;i++) {
            for (int f=1;f<=n-1;f++) {
                if (arr[f].value <= arr[f + 1].value) continue;
                swapel(f,f+1);

                delay();
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    public void heapify(int maxs,int id)
    {
        int largest = id;
        int l = 2 * id + 1;
        int r = 2 * id + 2;
        if (l < maxs && arr[l+1].value > arr[largest+1].value)
            largest = l;
        if (r < maxs && arr[r+1].value > arr[largest+1].value)
            largest = r;
        if (largest != id) {
            swapel(id+1, largest+1);
            delay();
            panel.revalidate();
            panel.repaint();
            heapify(maxs,largest);
        }
    }
    public void heapSort() {
        for (int i = n/2-1; i >= 0; i--)
            heapify(n,i);
        for (int i = n-1; i > 0; i--) {
            swapel(0+1,i+1);
            delay();
            panel.revalidate();
            panel.repaint();
            heapify(i,0);
        }
    }
    public void mergeSort(int l, int r){
        int mid = (l+r)/2 , ptr=mid+1;
        int to =l;
        if(l==r) return;
        else{
            mergeSort(l, mid);
            mergeSort(mid+1, r);

            for(int i = l; i<=mid; i++){
                while(ptr<=r && arr[ptr].value<=arr[i].value){
                    temp[to]=arr[ptr];
                    to++; ptr++;
                }

                temp[to]=arr[i]; to++;
            }

            while(ptr<=r){
                temp[to]=arr[ptr];
                to++; ptr++;
            }

            for(int i=l;i<=r;i++){
                arr[i]=temp[i];
                setval(i,arr[i].value);
                delay();
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    public void quickSort(int l,int r){
        System.out.println(l + " " + r);
        if(l>=r)return;

        int mid=(l+r)/2;
        int pivot=arr[mid].value,br=0,ptr=r;

        for(int i=l;i<=r;i++){
            if(arr[i].value<pivot)br++;
        }
        int pospivot=l+br;

        swapel(mid,pospivot);
        delay();
        panel.revalidate();
        panel.repaint();

        for(int i=l;i<pospivot;i++){
            while(ptr>pospivot && arr[ptr].value>pivot)ptr--;

            if(arr[i].value>pivot){
                swapel(i,ptr);
                delay();
                panel.revalidate();
                panel.repaint();
            }
        }

        quickSort(l,pospivot-1);
        quickSort(pospivot+1,r);
    }

    public void selectionSort(){
        for(int i=1; i<=n; i++){
            int min = arr[i].value;
            int minIndex = i;
            for(int k=i; k<=n; k++){
                if(arr[k].value < min) {
                    min = arr[k].value;
                    minIndex = k;
                }
            }
            swapel(i, minIndex);
            delay();
            panel.revalidate();
            panel.repaint();
        }
    }

    public void insertionSort(){
        for(int i=2; i<=n; i++){
            int currentElement = arr[i].value;
            int k;
            for(k=i-1; k>=1 && arr[k].value>currentElement; k--){
                setval(k+1, arr[k].value);
                delay();
                panel.revalidate();
                panel.repaint();
            }
            setval(k+1, currentElement);
            delay();
            panel.revalidate();
            panel.repaint();
        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1400,800);

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setBackground(new Color(129, 124, 124));
        contentPane.setForeground(new Color(0, 0, 0));
        contentPane.setLayout(null);

        panel.setBounds(100, 100, 1000, 500);
        panel.setBackground(new Color(52, 58, 63));
        panel.setForeground(new Color(0, 0, 0));
        panel.setLayout(null);
        contentPane.add(panel);

        JLabel typesize = new JLabel("Type number of elements:");
        typesize.setHorizontalAlignment(SwingConstants.CENTER);
        typesize.setFont(new Font("Tahoma", Font.PLAIN, 20));
        typesize.setBounds(100, 25, 300, 50);
        contentPane.add(typesize);

        JTextField arrsize = new JTextField();
        arrsize.setHorizontalAlignment(SwingConstants.CENTER);
        arrsize.setFont(new Font("Tahoma", Font.PLAIN, 20));
        arrsize.setBounds(400, 25, 100, 50);
        contentPane.add(arrsize);

        JLabel typespeed = new JLabel("Choose speed:");
        typespeed.setHorizontalAlignment(SwingConstants.CENTER);
        typespeed.setFont(new Font("Tahoma", Font.PLAIN, 20));
        typespeed.setBounds(500, 25, 200, 50);
        contentPane.add(typespeed);

        JTextField speedval = new JTextField();
        speedval.setHorizontalAlignment(SwingConstants.CENTER);
        speedval.setFont(new Font("Tahoma", Font.PLAIN, 20));
        speedval.setBounds(700, 25, 100, 50);
        contentPane.add(speedval);

        JButton runsort = new JButton("Run");
        runsort.setHorizontalAlignment(SwingConstants.CENTER);
        runsort.setFont(new Font("Tahoma", Font.PLAIN, 20));

        runsort.setBackground(new Color(13, 222, 215));
        runsort.setForeground(new Color(255, 255, 255));
        runsort.setBounds(1150,100,150,50);
        contentPane.add(runsort);

        JLabel statusbar = new JLabel("Status: initialize");
        statusbar.setHorizontalAlignment(SwingConstants.CENTER);
        statusbar.setFont(new Font("Tahoma", Font.PLAIN, 20));
        statusbar.setBounds(1100, 25, 250, 50);
        contentPane.add(statusbar);

        String[] stringWithSorts = {"Bubble sort", "Selection sort", "Insertion sort", "Merge sort", "Quick sort", "Heap sort"};
        JComboBox comboBox = new JComboBox(stringWithSorts);
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        comboBox.setBounds(850, 25, 250, 50);
        contentPane.add(comboBox);

        runsort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                n=Integer.valueOf(arrsize.getText());
                speed=Integer.valueOf(speedval.getText());

               if(n<10 || n>200 || speed<1 || speed>100){
                    return;
                }

                statusbar.setText("Status: shuffle");
                for(int i=1;i<=n;i++) {
                    arr[i] = new Element(i);
                    arr[i].value = i;
                    setval(i,i);
                    temp[i] = new Element(i);
                    temp[i].value = i;
                }

                panel.removeAll();
                for(int i=1;i<=n;i++){
                    arr[i].setBackground(new Color(96, 234, 228));
                    arr[i].setForeground(new Color(255, 255, 255));
                    arr[i].setOpaque(true);

                    arr[i].setHorizontalAlignment(SwingConstants.CENTER);
                    arr[i].setFont(new Font("Arial", Font.PLAIN, 18));
                    panel.add(arr[i]);
                }

                new Thread(() -> {
                    for(int i=1;i<=n;i++) {
                        int ind = rand.nextInt(n) + 1;
                        swapel(i, ind);

                        delay();
                        panel.revalidate();
                        panel.repaint();
                    }

                    statusbar.setText("Status: sort");
                    for(int i=1;i<=50;i++)delay();

                    if(comboBox.getSelectedItem() == "Bubble sort") bubbleSort();
                    if(comboBox.getSelectedItem() == "Selection sort") selectionSort();
                    if(comboBox.getSelectedItem() == "Insertion sort") insertionSort();
                    if(comboBox.getSelectedItem() == "Merge sort") mergeSort(1, n);
                    if(comboBox.getSelectedItem() == "Quick sort") quickSort(1, n);
                    if(comboBox.getSelectedItem() == "Heap sort") heapSort();
                    statusbar.setText("Status: initialize");
                }).start();
            }
        });
    }
}
