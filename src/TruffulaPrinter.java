import java.io.Console;
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.*;

/**
 * TruffulaPrinter is responsible for printing a directory tree structure
 * with optional colored output. It supports sorting files and directories
 * in a case-insensitive manner and cycling through colors for visual clarity.
 */
public class TruffulaPrinter {
  
  /**
   * Configuration options that determine how the tree is printed.
   */
  private TruffulaOptions options;
  
  /**
   * The sequence of colors to use when printing the tree.
   */
  private List<ConsoleColor> colorSequence;
  
  /**
   * The output printer for displaying the tree.
   */
  private ColorPrinter out;

  /**
   * Default color sequence used when no custom colors are provided.
   */
  private static final List<ConsoleColor> DEFAULT_COLOR_SEQUENCE = List.of(
      ConsoleColor.WHITE, ConsoleColor.PURPLE, ConsoleColor.YELLOW
  );

  /**
   * Constructs a TruffulaPrinter with the given options, using the default
   * output stream and the default color sequence.
   *
   * @param options the configuration options for printing the tree
   */
  public TruffulaPrinter(TruffulaOptions options) {
    this(options, System.out, DEFAULT_COLOR_SEQUENCE);
  }

  /**
   * Constructs a TruffulaPrinter with the given options and color sequence,
   * using the default output stream.
   *
   * @param options the configuration options for printing the tree
   * @param colorSequence the sequence of colors to use when printing
   */
  public TruffulaPrinter(TruffulaOptions options, List<ConsoleColor> colorSequence) {
    this(options, System.out, colorSequence);
  }

  /**
   * Constructs a TruffulaPrinter with the given options and output stream,
   * using the default color sequence.
   *
   * @param options the configuration options for printing the tree
   * @param outStream the output stream to print to
   */
  public TruffulaPrinter(TruffulaOptions options, PrintStream outStream) {
    this(options, outStream, DEFAULT_COLOR_SEQUENCE);
  }

  /**
   * Constructs a TruffulaPrinter with the given options, output stream, and color sequence.
   *
   * @param options the configuration options for printing the tree
   * @param outStream the output stream to print to
   * @param colorSequence the sequence of colors to use when printing
   */
  public TruffulaPrinter(TruffulaOptions options, PrintStream outStream, List<ConsoleColor> colorSequence) {
    this.options = options;
    this.colorSequence = colorSequence;
    out = new ColorPrinter(outStream);
  }

  /**
   * WAVE 4: Prints a tree representing the directory structure, with directories and files
   * sorted in a case-insensitive manner. The tree is displayed with 3 spaces of
   * indentation for each directory level.
   * 
   * WAVE 5: If hidden files are not to be shown, then no hidden files/folders will be shown.
   *
   * WAVE 6: If color is enabled, the output cycles through colors at each directory level
   * to visually differentiate them. If color is disabled, all output is displayed in white.
   *
   * WAVE 7: The sorting is case-insensitive. If two files have identical case-insensitive names,
   * they are sorted lexicographically (Cat.png before cat.png).
   *
   * Example Output:
   *
   * myFolder/
   *    Apple.txt
   *    banana.txt
   *    Documents/
   *       images/
   *          Cat.png
   *          cat.png
   *          Dog.png
   *       notes.txt
   *       README.md
   *    zebra.txt
   */
  public void printTree() {
    // TODO: Implement this!
    
    // Hints:
    // - Add a recursive helper method
    // - For Wave 6: Use AlphabeticalFileSorter
    // DO NOT USE SYSTEM.OUT.PRINTLN
    // USE out.println instead (will use your ColorPrinter)
    // getName() ==> returns the name of the file or directory denoted by this abstract path name
    // isDirectory() ==> Tests whether the file denoted by this abstract pathname is a directory.
    // isHidden() ==> Tests whether the file named by this abstract pathname is a hidden file.
    out.setCurrentColor(ConsoleColor.WHITE);
    out.println(options.getRoot().getName()+ "/");
    printTreeHelper(options.getRoot(), 1);

    //out.println("printTree was called!");
    //out.println("My options are: " + options);
  }

  private void printTreeHelper(File directory, int level) {
    if (directory == null || !directory.isDirectory()) return;
    
    
    File[] files = directory.listFiles();
    if(files != null){

      files = AlphabeticalFileSorter.sort(files);

      for(File file : files){
        //StringBuilder str = new StringBuilder();
        String indent = "   ".repeat(level);
        if (options.isUseColor()) {
          ConsoleColor color = getColorForLevel(level);
          out.setCurrentColor(color);
        } else {
            
            out.setCurrentColor(ConsoleColor.WHITE);
        }

        out.println(indent + (file.isDirectory() ? file.getName() + "/" : file.getName()));


        if(file.isDirectory()){
          printTreeHelper(file, level +1);
        }
      }
    }
  }
  

  private ConsoleColor getColorForLevel(int level) {
    // Cycle through the colors based on the level of the directory
    switch (level % 3) {
        case 0:
            return ConsoleColor.WHITE; //root white and child of yellow white
        case 1:
            return ConsoleColor.PURPLE; 
        case 2:
            return ConsoleColor.YELLOW;  
        default:
            return ConsoleColor.WHITE;  
    }
  }
}