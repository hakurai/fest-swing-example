/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.navalvessel.demo;

import javax.swing.JLabel;
import org.fest.swing.core.EmergencyAbortListener;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.fixture.JLabelFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.fest.swing.launcher.ApplicationLauncher;
import org.fest.swing.testing.FestSwingTestCaseTemplate;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author eguchi
 */
public class SampleFrameTest extends FestSwingTestCaseTemplate{

    private static EmergencyAbortListener mEmergencyAbortListener;
    private FrameFixture mFrame;

    public SampleFrameTest(){
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
        
        //mainメソッドから実行
        ApplicationLauncher.application( SampleFrame.class ).start();
        mFrame = WindowFinder.findFrame( SampleFrame.class ).using( robot() );
    }

    @After
    public void tearDown(){
        cleanUp();
    }

    @Test
    public void testJTextField(){
        System.out.println( "testJTextField" );

        mFrame.textBox( "srcField" ).requireEditable();
        mFrame.textBox( "destField" ).requireNotEditable();

        mFrame.textBox( "srcField" ).enterText( "hello world!" );
        mFrame.button( "copyButton" ).click();
        mFrame.textBox( "destField" ).requireText( "hello world!" );

        mFrame.textBox( "srcField" ).deleteText();
        mFrame.textBox( "srcField" ).enterText( "あいうえお" );
        mFrame.button( "copyButton" ).click();
        mFrame.textBox( "destField" ).requireText( "あいうえお" );

        mFrame.button( "resetButton" ).click();
        mFrame.textBox( "destField" ).requireText( "" );

    }

    @Test
    public void testJList(){
        System.out.println( "testJList" );

        mFrame.list( "jlist" ).requireNoSelection();

        mFrame.list( "jlist" ).selectItem( "Item 1" );
        mFrame.list( "jlist" ).requireSelectedItems( "Item 1" );
        mFrame.panel( "jlistPanel" ).textBox( "selectedField" ).requireText( "Item 1" );

        mFrame.list( "jlist" ).selectItem( "Item 3" );
        mFrame.list( "jlist" ).requireSelectedItems( "Item 3" );
        mFrame.panel( "jlistPanel" ).textBox( "selectedField" ).requireText( "Item 3" );

        mFrame.list( "jlist" ).selectItems( "Item 3", "Item 1" );
        mFrame.list( "jlist" ).requireSelectedItems( "Item 1", "Item 3" );
        mFrame.panel( "jlistPanel" ).textBox( "selectedField" ).requireText( "Item 1" );
    }

    @Test
    public void testJTree(){
        System.out.println( "testJTree" );

        mFrame.tree( "jtree" ).requireNoSelection();
        mFrame.tree( "jtree" ).node( "JTree/colors" ).click();

        mFrame.tree( "jtree" ).node( "JTree/sports" ).expand();
        mFrame.tree( "jtree" ).selectPath( "JTree/sports/soccer" );
        mFrame.tree( "jtree" ).requireSelection( "JTree/sports/soccer" );
        mFrame.panel( "jtreePanel" ).textBox( "selectedField" ).requireText( "soccer" );

        mFrame.tree( "jtree" ).node( "JTree/food" ).expand();
        mFrame.tree( "jtree" ).selectPath( "JTree/food/bananas" );
        mFrame.tree( "jtree" ).requireSelection( "JTree/food/bananas" );
        mFrame.panel( "jtreePanel" ).textBox( "selectedField" ).requireText( "bananas" );
    }

    @Test
    public void testCalc(){
        System.out.println( "testCalc" );

        JTextComponentFixture value1 = mFrame.textBox( "value1" );
        JTextComponentFixture value2 = mFrame.textBox( "value2" );
        JTextComponentFixture ans = mFrame.textBox( "answerField" );
        JComboBoxFixture op = mFrame.comboBox( "operator" );

        value1.requireText( "" );
        value2.requireText( "" );
        ans.requireText( "" );
        op.requireSelection( "+" );

        value1.enterText( "3" );
        value2.enterText( "3" );
        ans.requireText( "6" );

        op.selectItem( "-" );
        ans.requireText( "0" );

        op.selectItem( "-" );
        ans.requireText( "0" );

        op.selectItem( "*" );
        ans.requireText( "9" );

        op.selectItem( "/" );
        ans.requireText( "1" );

        op.selectItem( "+" );
        ans.requireText( "6" );

        value1.deleteText().enterText( "10" );
        value2.deleteText().enterText( "100" );
        ans.requireText( "110" );

        value1.deleteText().enterText( "10" );
        value2.deleteText().enterText( "-100" );
        ans.requireText( "-90" );

        op.selectItem( "-" );
        ans.requireText( "110" );

        op.selectItem( "*" );
        ans.requireText( "-1000" );

        op.selectItem( "/" );
        ans.requireText( "-0.1" );

        value1.deleteText().enterText( "3" );
        value2.deleteText().enterText( "0" );
        op.selectItem( "/" );
        ans.requireText( "error" );
    }

    @Test
    public void testThread(){
        System.out.println( "testThread" );
        
        Timeout timeout = Timeout.timeout( 60000 );

        JLabelFixture label = mFrame.label( "value" );
        label.requireText( "1" );
        mFrame.button( "incrementButton" ).click().requireDisabled();
        Pause.pause( isSameText( label, "2" ), timeout );
        mFrame.button( "incrementButton" ).requireEnabled();
        
        mFrame.button( "incrementButton" ).click().requireDisabled();
        Pause.pause( isSameText( label, "3" ), timeout );
        mFrame.button( "incrementButton" ).requireEnabled();
        
        mFrame.button( "incrementButton" ).click().requireDisabled();
        Pause.pause( isSameText( label, "4" ), timeout );
        mFrame.button( "incrementButton" ).requireEnabled();
        
        mFrame.button( "incrementButton" ).click().requireDisabled();
        Pause.pause( isSameText( label, "5" ), timeout );
        mFrame.button( "incrementButton" ).requireEnabled();

    }
    
    private Condition isSameText( JLabelFixture label, String text ){
        return new LabelTextCondition( label.component(), text);
    }

    class LabelTextCondition extends Condition{
        
        private JLabel mLabel;
        private String mText;
        LabelTextCondition( JLabel label, String text ){
            super( "label text to be " + text );
            mLabel = label;
            mText = text;
        }

        @Override
        public boolean test(){
            return mLabel.getText().equals( mText );
        }
        
        @Override
        protected void done(){
            mLabel = null;
            
        }
    }
}
