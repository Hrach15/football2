function Pause(){
    [System.Console]::Write('Press any key to continue...')
    [void][System.Console]::ReadKey(1)
 }


$ShowUIPattern = """ShowPromoteUI""="
$NewFeaturePattern = """NewFeatureName""="
$regFile = "C:\ProgramData\ASUS\ASUS System Control Interface\AsusSystemDiagnosis\Setting.reg"
$regKey = "HKLM\_TMP"

#LinkToMyASUS as FeatureName in setting
$featureNameStr = """NewFeatureName""=hex(5f5e10c):4c,00,69,00,6e,00,6b,00,54,00,6f,00,4d,00,79,00,41,00,53,00,55,00,53,00,00,00,36,88,e1,5d,bd,60,d6,01"
$showUIStr  = """ShowPromoteUI""=hex(5f5e10b):00,77,34,3f,3b,26,5c,d6,01"

$placeHoder1 = "#*placeholder1"
$placeHoder2 = "#*placeholder2"
$hasShowFeatureUIKey = $false
$hasFeatureNameKey = $false
$blSucceed = $true

Write-Output ".................."$(Get-Date)".................."

#load setting.dat file
Write-Output "Load setting.dat file to registry."
reg load $regKey "$env:AppData\..\Local\Packages\B9ECED6F.ASUSPCAssistant_qmba6cd70vzyy\Settings\settings.dat"
if($LASTEXITCODE -eq 1)
{
    #Failed to load setting.dat to registry
    Write-Output 'Failed to load setting.dat to registry.'
    $blSucceed = $false
    exit
}


#export setting.reg file
Write-Output "Export registry to setting.reg file."
reg export $regKey "$regFile" /y
if($LASTEXITCODE -eq 1)
{
    #Failed to load setting.dat to registry
    Write-Output 'Failed to export Setting.reg from registry.'

    reg unload $regKey
    $blSucceed = $false
    exit
}


#Modify setting.reg file
Write-Output "Begin to modify setting.reg file."
$fileContents = Get-Content $regFile
$finalContents = @()
foreach ($line in $fileContents) 
{ 
    if($line.Contains("[HKEY_LOCAL_MACHINE\_TMP\LocalState]"))
    {
         $finalContents += $line
         $finalContents += $placeHoder1
         $finalContents += $placeHoder2
         continue
    }

 
    #If has ShowPromoteUI field
    if($line.StartsWith($ShowUIPattern))
    {
        Write-Output "Current reg file has ShowPromoteUI key."
	    $hasShowFeatureUIKey = $true
        #ShowPromoteUI in Current setting file is false, Do not change setting file and exit.
        if($line.Contains("hex(5f5e10b):00"))
        {
            $finalContents += $line     
        }
        else
        {
            #ShowPromoteUI in Current setting file is true,change it to false
            $tempValue2 = $line.Replace("hex(5f5e10b):01","hex(5f5e10b):00")
            $finalContents += $tempValue2
            Write-Output "Modify ShowPromoteUI key and value as :"$tempValue2"."
        }
        
	}
   else 
   {
       if($line.StartsWith($NewFeaturePattern))
       {
            Write-Output "Current reg file has NewFeatureName key."
            $hasFeatureNameKey = $true
       }

       $finalContents += $line     
   }
	
}

if($hasShowFeatureUIKey -eq $true)
{
    $finalContents = $finalContents.Replace($placeHoder1,"")
}
else
{
    $finalContents = $finalContents.Replace($placeHoder1,$showUIStr)
}

if($hasFeatureNameKey -eq $true)
{
    $finalContents = $finalContents.Replace($placeHoder2,"")
}
else
{
     $finalContents = $finalContents.Replace($placeHoder2,$featureNameStr)
}

# dump new file contents to file
Write-Output "write new contents to file."
$finalContents | Out-File $regFile

#import setting.reg file to registry
Write-Output "Import setting.reg file to registry."
reg import $regFile
if($LASTEXITCODE -eq 1)
{
     #Failed to load setting.dat to registry
    Write-Output 'Failed to import Setting.reg to registry.'
    $blSucceed = $false
}

#unload registry and write back to setting.dat file
reg unload $regKey
Write-Output "Import registry to setting.dat file."
if($LASTEXITCODE -eq 1)
{
     #Failed to load setting.dat to registry.
    Write-Output 'Failed to unload Setting.dat from registry.'
    $blSucceed = $false
}
else
{
    Write-Output 'The operation was completed successfully.'
}

#remove setting.reg file
Write-Output "Remove reg file."
Remove-Item $regFile
if($LASTEXITCODE -eq 1)
{
     #Failed to load setting.dat to registry
    Write-Output 'Failed to Remove Setting.reg file.'
    $blSucceed = $false
}



if($blSucceed -eq $true)
{
    Write-Output 'Succeed to modify MyASUS setting file, Create Modify.set file'
    #Create Modify.set file,to tell systemdiagnosis server MyASUS setting has modified.
    New-Item  "$env:LOCALAPPDATA\Packages\B9ECED6F.ASUSPCAssistant_qmba6cd70vzyy\Settings\Modify.set" -type file
    Write-Output 'Create Modify.set file,to tell systemdiagnosis server MyASUS setting has modified.'

    #delete MyASUSTask Task schedule
    #if ($(Get-ScheduledTask -TaskName "MyASUSTask" -ErrorAction SilentlyContinue).TaskName -eq "MyASUSTask") 
    #{
    #   Unregister-ScheduledTask -TaskName "MyASUSTask" -Confirm:$False
    #}
    #Write-Host 'delete MyASUSTask Task schedule'
}

Write-Output 'Complete'