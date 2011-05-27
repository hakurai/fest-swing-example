/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.demo;

import org.fest.swing.core.EmergencyAbortListener;


import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.fixture.JTableFixture;
import org.fest.swing.security.NoExitSecurityManagerInstaller;
import org.fest.swing.testing.FestSwingTestCaseTemplate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.fest.swing.data.TableCell.*;
import static org.fest.swing.data.Index.*;

/**
 *
 * @author eguchi
 */
public class SsmpleFrame2Test extends FestSwingTestCaseTemplate{

    private static EmergencyAbortListener mEmergencyAbortListener;
    private FrameFixture mFrame;

    public SsmpleFrame2Test(){
    }

    @BeforeClass
    public static void setUpClass() throws Exception{
        //EmergencyAbortListener を使用すると CTRL + SHIFT + A でテストを停止できる
        mEmergencyAbortListener = EmergencyAbortListener.registerInToolkit();
    }

    @AfterClass
    public static void tearDownClass() throws Exception{
        mEmergencyAbortListener.unregister();
    }

    @Before
    public void setUp(){
        setUpRobot();
        //入力の遅延時間を設定できる
        //入力を高速化するには時間を短くする
//        robot().settings().eventPostingDelay( 5 );
//        robot().settings().delayBetweenEvents( 5 );

        //直接インスタンス生成
        mFrame = new FrameFixture( robot(), new SampleFrame2() );
        mFrame.show();
    }

    @After
    public void tearDown(){
        cleanUp();
    }

    @Test
    public void testJTabbedPane(){
        System.out.println( "testJTabbedPane" );

        mFrame.tabbedPane().requireTitle( "tab1", atIndex( 0 ) );
        mFrame.tabbedPane().requireTitle( "tab2", atIndex( 1 ) );
        mFrame.tabbedPane().requireTitle( "tab3", atIndex( 2 ) );

        mFrame.textBox().enterText( "Java（ジャバ）は、狭義ではオブジェクト指向プログラミング言語Javaであり、広義ではプログラミング言語Javaのプログラムの実行環境および開発環境をいう。本稿ではプログラミング言語としてのJava、および関連する技術や設計思想、およびJava言語の実行環境としてみたJavaプラットフォームについて解説する。クラスライブラリなどを含めた、Javaバイトコードの実行環境と開発環境（広義のJava）については、Javaプラットフォームを参照。また、言語の文法に関してはJavaの文法を参照。" );

        mFrame.tabbedPane().selectTab( 1 );
        mFrame.textBox().enterText( "Groovy（グルービー）は、Javaプラットフォーム (Java仮想マシン、JVM) 上で動作するアジャイルな動的言語である。\n"
                + "Groovyの処理系はオープンソースソフトウェアであり、James StrachanとBob McWhirterらを中心に、オープンソース開発サイトであるcodehaus上でBSD/Apacheライクなライセンスにて、2003年8月27日に開発が開始された（CVSへの最初のコミットがなされた）。その後、開発の主体はGuillaume LaforgeとJeremy Raynerらに移り開発が続けられている。" );

        mFrame.tabbedPane().selectTab( 2 );
        mFrame.textBox().enterText( "Scala (スカラ、スカーラ、スケイラ、Scalable Language) はオブジェクト指向言語と関数型言語の特徴を統合したマルチパラダイムのプログラミング言語である。" );

    }

    @Test
    public void testJTable(){
        System.out.println( "testJTable" );

        JTableFixture table = mFrame.table( "table" );

        table.cell( row( 0 ).column( 0 ) ).requireValue( "true" );
        table.cell( row( 0 ).column( 1 ) ).requireValue( "apple" );
        table.cell( row( 0 ).column( 2 ) ).requireValue( "300" );
        table.cell( row( 0 ).column( 3 ) ).requireValue( "1" );

        table.cell( row( 1 ).column( 0 ) ).requireValue( "false" ).click().requireValue( "true" );
        table.cell( row( 1 ).column( 1 ) ).requireValue( "" ).enterValue( "orange" ).requireValue( "orange" );
        table.cell( row( 1 ).column( 2 ) ).requireValue( "" ).enterValue( "100" ).requireValue( "100" );
        table.cell( row( 1 ).column( 3 ) ).requireValue( "" ).enterValue( "3" ).requireValue( "3" );

        table.cell( row( 2 ).column( 0 ) ).requireValue( "false" );
        table.cell( row( 2 ).column( 1 ) ).requireValue( "" ).enterValue( "lemon" ).requireValue( "lemon" );
        table.cell( row( 2 ).column( 2 ) ).requireValue( "" ).enterValue( "150" ).requireValue( "150" );
        table.cell( row( 2 ).column( 3 ) ).requireValue( "" ).enterValue( "0" ).requireValue( "0" );

        table.cell( row( 3 ).column( 0 ) ).requireValue( "false" );
        table.cell( row( 3 ).column( 1 ) ).requireValue( "" ).enterValue( "banana" ).requireValue( "banana" );
        table.cell( row( 3 ).column( 2 ) ).requireValue( "" ).enterValue( "200" ).requireValue( "200" );
        table.cell( row( 3 ).column( 3 ) ).requireValue( "" ).enterValue( "10" ).requireValue( "10" );
    }

    @Test
    public void testJOptionPane(){
        System.out.println( "testJOptionPane" );

        mFrame.button( "showOptionPane" ).click();

        JOptionPaneFixture optionPane = mFrame.optionPane();

        optionPane.requireVisible();
        optionPane.requireInformationMessage();
        optionPane.requireTitle( "option" );
        optionPane.requireMessage( "Hello FEST-Swing" );
        optionPane.yesButton().requireVisible();
        optionPane.noButton().requireVisible();
        optionPane.yesButton().click();

        mFrame.button( "showOptionPane" ).click();

        optionPane = mFrame.optionPane();

        optionPane.requireVisible();
        optionPane.noButton().click();
    }

    @Test
    public void testJCkeckBox(){
        System.out.println( "testJCkeckBox" );

        mFrame.checkBox( "check1" ).check().requireSelected();
        mFrame.checkBox( "check2" ).check().requireSelected();
        mFrame.checkBox( "check3" ).check().requireSelected();

        mFrame.checkBox( "check2" ).uncheck().requireNotSelected();
    }

    @Test
    public void testJRadioButton(){
        System.out.println( "testJRadioButton" );

        mFrame.radioButton( "radio1" ).requireSelected();
        mFrame.radioButton( "radio2" ).requireNotSelected();
        mFrame.radioButton( "radio3" ).requireNotSelected();

        mFrame.radioButton( "radio2" ).check();
        mFrame.radioButton( "radio1" ).requireNotSelected();
        mFrame.radioButton( "radio2" ).requireSelected();
        mFrame.radioButton( "radio3" ).requireNotSelected();

        mFrame.radioButton( "radio3" ).check();
        mFrame.radioButton( "radio1" ).requireNotSelected();
        mFrame.radioButton( "radio2" ).requireNotSelected();
        mFrame.radioButton( "radio3" ).requireSelected();

    }
    
    @Test
    public void testMenu(){
        System.out.println( "testJRadioButton" );

        //System.exitされないようにする
        NoExitSecurityManagerInstaller noExit = NoExitSecurityManagerInstaller.installNoExitSecurityManager();
        
        mFrame.menuItemWithPath( "File", "exit" ).click();
        
        noExit.uninstall();
        
    }
}
