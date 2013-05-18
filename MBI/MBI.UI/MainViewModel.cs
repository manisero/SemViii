using System.Windows.Input;
using MBI.Logic.DNAAssemblance;
using MBI.Logic.Entities;
using MBI.Logic.Serialization;
using MBI.UI.Commands;
using Microsoft.Win32;
using System.Linq;

namespace MBI.UI
{
	public class MainViewModel
	{
		#region Dependencies

		private readonly IAssemblyParser _assemblyParser;
		private readonly IDNAAssembler _dnaAssembler;
		private readonly IScaffoldSerializer _scaffoldSerializer;

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

		public MainViewModel(IAssemblyParser assemblyParser, IDNAAssembler dnaAssembler, IScaffoldSerializer scaffoldSerializer)
		{
			_assemblyParser = assemblyParser;
			_dnaAssembler = dnaAssembler;
			_scaffoldSerializer = scaffoldSerializer;
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
			var scaffolds = _dnaAssembler.Assemble(Assembly.Contigs, Assembly.PairedEndTags);

			var dialog = new SaveFileDialog();
			dialog.ShowDialog();

			if (!string.IsNullOrEmpty(dialog.FileName))
			{
				_scaffoldSerializer.Serialize(scaffolds.First(), dialog.OpenFile());
			}
		}

		#endregion
	}
}
