using System.Windows;
using System.Windows.Input;
using MBI.Logic;
using MBI.Logic.AssemblyParsing;
using MBI.UI.Commands;
using Microsoft.Win32;

namespace MBI.UI
{
	public class MainViewModel
	{
		#region Dependencies

		private readonly IAssemblyParser _assemblyParser;

		#endregion

		#region Properties

		public ICommand ChooseFile
		{
			get { return new Command(GetInputFile); }
		}

		public ICommand Assemble
		{
			get { return new Command(ExecuteAssemblance); }
		}

		public DNAAssembly Assembly { get; set; }

		#endregion

		#region Constructor

		public MainViewModel(IAssemblyParser assemblyParser)
		{
			_assemblyParser = assemblyParser;
		}

		#endregion

		#region Private methods

		private void GetInputFile()
		{
			var dialog = new OpenFileDialog();
			dialog.ShowDialog();

			if (!string.IsNullOrEmpty(dialog.FileName))
			{
				Assembly = _assemblyParser.Parse(dialog.OpenFile());
			}
		}

		private void ExecuteAssemblance()
		{
			MessageBox.Show("foo");
		}

		#endregion
	}
}
