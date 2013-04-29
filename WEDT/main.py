import sys
import utilities.webcontentsanalyzer as webcontentsanalyzer

if __name__ == "__main__":
	
	if len(sys.argv) > 1:
		
		analyzer = webcontentsanalyzer.WebContentsAnalyzer()

		analyzer.print_html_tree(sys.argv[1])

	else:

		print >> sys.stderr, 'Bad syntax. Use: ' + sys.argv[0] + ' url'
	
